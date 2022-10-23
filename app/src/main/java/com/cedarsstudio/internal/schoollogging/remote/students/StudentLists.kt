package com.cedarsstudio.internal.schoollogging.remote.students

import android.util.Log
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.AuthType
import com.cedarsstudio.internal.schoollogging.remote.students.models.DayStatus
import com.cedarsstudio.internal.schoollogging.remote.students.models.MonthDetails.getMonthDetails
import com.cedarsstudio.internal.schoollogging.remote.students.models.Student
import com.cedarsstudio.internal.schoollogging.remote.students.models.StudentRoster
import com.cedarsstudio.internal.schoollogging.remote.students.models.WeekDetailsDTO
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Suppress("LocalVariableName")
class StudentLists {

    companion object {
        const val TAG = "STUDENT_LIST"
        val coroutineDispatcher = Dispatchers.IO
        val studentsDB = Firebase.firestore.collection("Students")
        val rosterDB = Firebase.firestore.collection("Rosters")
    }

    suspend fun get(): Flow<Response<MutableList<Student>>> = callbackFlow {
        withContext(coroutineDispatcher) {
            try {
                Log.e(TAG, "get: Started")
                studentsDB.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySendBlocking(Response.Error(error.message))
                    } else {
                        val dbStudentsLists = mutableListOf<Student>()
                        val _dbStudentsLists = value?.documents
                        Log.e(TAG, "get: $_dbStudentsLists")
                        if (_dbStudentsLists != null && _dbStudentsLists.isNotEmpty()) {
                            dbStudentsLists.addAll(_dbStudentsLists.map { student ->
                                student.toObject(Student::class.java)!!
                            }.toMutableList())
                            trySendBlocking(Response.Success(dbStudentsLists))
//                            trySendBlocking(Response.Error("Known error."))
                        } else trySendBlocking(Response.Error("No item found."))
                    }
                }
                awaitCancellation()
            } catch (e: Exception) {
                trySendBlocking(Response.Error(e.message))
            }
        }
        awaitClose()
    }

    suspend fun create(_student: Student): Response<String> = withContext(coroutineDispatcher) {
        try {
            if (!_student.isEmpty()) {
                studentsDB.add(_student).await()
                Response.Success(msg = "Student Created")
            } else {
                Response.Error("Please fill out all fields to continue.")
            }
        } catch (e: Exception) {
            Response.Error(e.message)
        }
    }

    suspend fun roster(): Flow<Response<MutableList<StudentRoster>>> = callbackFlow {
        withContext(coroutineDispatcher) {
            try {
                Log.e(TAG, "roster: Started")
                rosterDB.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySendBlocking(Response.Error(error.message))
                    } else {
                        if (value != null) {
                            /*

                            val roster =
                                mutableListOf<MutableList<MutableList<MutableList<StudentRoster>>>>()
                            val monthRoster =
                                mutableListOf<MutableList<MutableList<StudentRoster>>>()
                            val weeksRoster = mutableListOf<MutableList<StudentRoster>>()
                            val weekRoster = mutableListOf<StudentRoster>()
                            weeksRoster.add(weekRoster)
                            monthRoster.add(weeksRoster)
                            roster.add(monthRoster)
                            Log.e(TAG, "roster: $roster")
                         val processedDocValues = value.documents.map { it.toObject(StudentRoster::class.java) }
                            val years = processedDocValues.groupBy { it?.monthDetail?.year }
//                            var months : Map<Int, List<>>

                            Log.e(TAG, "roster: Year group -> $years")
                            years.values.forEach {
                                Log.e(TAG, "roster: Months Group -> ${it.groupBy { it?.monthDetail?.month}}", )
                            }
*/
                            val _dbStudentsRosterLists = value.documents.map { it.toObject(StudentRoster::class.java)!! }.toMutableList()
                            trySendBlocking(Response.Success(_dbStudentsRosterLists))
                        } else {
                            throw Exception("No item found")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "roster: Error", e)
                trySendBlocking(Response.Error(e.message))
            }
        }
        awaitClose()
    }

    suspend fun setOrToggleAuthState(id: String, type: AuthType): Response<String> =
        withContext(coroutineDispatcher) {

            try {
                val monthDetails = getMonthDetails()
                val currentTimestamp = Timestamp.now()
                val currentAdmin = Firebase.auth.currentUser?.uid
                val dayStatus = if (type == AuthType.SignIn) {
                    DayStatus(
                        signedIn = true,
                        signedInTimeStamp = currentTimestamp,
                        signedInAdmin = currentAdmin
                    )
                } else {
                    DayStatus(
                        signedOut = true,
                        signedOutTimeStamp = currentTimestamp,
                        signedOutAdmin = currentAdmin
                    )
                }
                val roster = when (monthDetails.day) {
                    2 -> WeekDetailsDTO(mon = dayStatus)
                    3 -> WeekDetailsDTO(tue = dayStatus)
                    4 -> WeekDetailsDTO(wed = dayStatus)
                    5 -> WeekDetailsDTO(thur = dayStatus)
                    6 -> WeekDetailsDTO(fri = dayStatus)
                    else -> WeekDetailsDTO()
                }
                val successMsg =
                    if (type == AuthType.SignIn) "$id is now signed in" else "$id has being signed out"

                Log.e(TAG, "setOrToggleAuthState MonthDetails: $monthDetails")
                val rosterDBCheck =
                    rosterDB.whereEqualTo("studentId", id).whereEqualTo("monthDetail", monthDetails)
                        .get().await()
                if (!rosterDBCheck.isEmpty) {
                    val foundDocRaw = rosterDBCheck.documents.first()
                    val foundDoc = foundDocRaw.toObject(StudentRoster::class.java)
                    val foundDayStatus = when (monthDetails.day) {
                        2 -> foundDoc?.weekDetails?.mon
                        3 -> foundDoc?.weekDetails?.tue
                        4 -> foundDoc?.weekDetails?.wed
                        5 -> foundDoc?.weekDetails?.thur
                        6 -> foundDoc?.weekDetails?.fri
                        else -> null
                    }
                    val foundDay = when (monthDetails.day) {
                        2 -> "mon"
                        3 -> "tue"
                        4 -> "wed"
                        5 -> "thur"
                        6 -> "fri"
                        else -> null
                    }
                    if (foundDayStatus?.signedIn == true && type == AuthType.SignIn) {
                        Response.Error("$id is already signed in")
                    } else if (foundDayStatus?.signedOut == true && type == AuthType.SignOut) {
                        Response.Error("$id is already out")
                    } else {
                        if (foundDayStatus?.signedIn == false && type == AuthType.SignOut) {
                            Response.Error("$id was not signed in")
                        } else {
                            val authTypeKey =
                                if (type == AuthType.SignIn) "signedIn" else "signedOut"
                            val authTypeAdminKey =
                                if (type == AuthType.SignIn) "signedInAdmin" else "signedOutAdmin"
                            val authTypeTimeStampKey =
                                if (type == AuthType.SignIn) "signedInTimeStamp" else "signedOutTimeStamp"
                            rosterDB.document(foundDocRaw.id).update(
                                "weekDetails.$foundDay.$authTypeKey", true,
                                "weekDetails.$foundDay.$authTypeTimeStampKey", currentTimestamp,
                                "weekDetails.$foundDay.$authTypeAdminKey", currentAdmin,
                            ).await()
                            Response.Success(msg = successMsg)
                        }
                    }
                } else {
                    if (monthDetails.day == 1 || monthDetails.day == 7) {
                        Response.Error(msg = "Sorry can't use this feature on a none school day")
                    } else {
                        rosterDB.add(
                            StudentRoster(
                                id, roster
                            )
                        ).await()
                        Response.Success(msg = successMsg)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "signIn Error", e)
                Response.Error(e.message)
            }
        }


}

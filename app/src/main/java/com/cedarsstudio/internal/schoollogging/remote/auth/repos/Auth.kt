package com.cedarsstudio.internal.schoollogging.remote.auth.repos

import android.util.Log
import com.cedarsstudio.internal.schoollogging.remote.auth.models.AdminProfile
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Auth {
    private var auth: FirebaseAuth = Firebase.auth
    private var userDB: DatabaseReference =
        Firebase.database.reference.child("Users").child("Admins")
//    private var userDB: CollectionReference = Firebase.firestore.collection("Users")

    companion object {
        private val coroutineContext = Dispatchers.IO
        private const val TAG = "AUTH"
    }

    suspend fun signIn(rememberMe: Boolean, email: String, password: String): Response<String> =
        withContext(coroutineContext) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                delay(4000)
                Response.Success("Successfully signed in. $rememberMe")
            } catch (e: Exception) {
                Response.Error(e.message)
            }
        }

    suspend fun signUp(name: String, email: String, password: String): Response<String> =
        withContext(coroutineContext) {
            try {
                Log.e(TAG, "signUp: Started")
                auth.createUserWithEmailAndPassword(email, password).await()
                val user = auth.currentUser!!
                Log.e(TAG, "signUp: Created")
                user.updateProfile(userProfileChangeRequest {
                    displayName = name
                }).await()
                Log.e(TAG, "signUp: Updated")
                userDB.child(user.uid).setValue(
                    AdminProfile(
                        id = user.uid, email = user.email, name = user.displayName
                    )
                ).await()
                Log.e(TAG, "signUp: SET")
                Response.Success("Success")
            } catch (e: Exception) {
                Response.Error(e.message)
            }
        }

    suspend fun requestResetPassword(email: String): Response<String> =
        withContext(coroutineContext) {
            try {
                auth.sendPasswordResetEmail(email)
                Response.Success()
            } catch (e: Exception) {
                Response.Error(e.message)
            }
        }

    suspend fun sendOTP(email: String): Response<String> = withContext(coroutineContext) {
        Response.Error()
    }

    suspend fun verifyOTP(code: Int): Response<String> = withContext(coroutineContext) {
        Response.Error()
    }

    suspend fun resetPassword(
        email: String, newPassword: String, confirmPassword: String
    ): Response<String> = withContext(coroutineContext) {
        Response.Error()
    }

    suspend fun signOut(): Response<String> = withContext(coroutineContext) {
        try {
//            INVALID OTHER USER RESOURCES
            auth.signOut()
            Response.Success()
        } catch (e: Exception) {
            Response.Error(e.message)
        }
    }

    fun isSignedIn(): Response<Boolean> = try {
        val user = auth.currentUser
        Response.Success(user != null)
    } catch (e: Exception) {
        Response.Error(e.message)
    }

    fun getUser(): Response<AdminProfile> = try {
        val admin: AdminProfile
        val user = auth.currentUser
        admin = AdminProfile(
            user?.uid, user?.displayName, user?.email, user?.photoUrl.toString()
        )
        Response.Success(
            admin
        )
    } catch (e: Exception) {
        Response.Error(e.message)
    }
}
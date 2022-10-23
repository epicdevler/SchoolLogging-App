package com.cedarsstudio.internal.schoollogging.remote.students.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

object MonthDetails {

    data class MonthDetail(
        var year: Int = 0,
        var month: Int = 0,
        var week: Int = 0,
        var day: Int = 0,
    )

    fun getMonthDetails(): MonthDetail {
        val details = MonthDetail()
        val calendar = Calendar.getInstance()
        return details.apply {
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            week = calendar[Calendar.WEEK_OF_MONTH]
            day = calendar[Calendar.DAY_OF_WEEK]
        }
    }
}


data class StudentRoster(
    val studentId: String? = null,
    val weekDetails: WeekDetailsDTO = WeekDetailsDTO(),
    val timestamp: Timestamp = Timestamp.now(),
    val monthDetail: MonthDetails.MonthDetail = MonthDetails.getMonthDetails()
)

data class StudentDetailsDTO(
    val studentID: String? = null,
    val studentName: String? = null,
)

data class WeekDetailsDTO(
    val mon: DayStatus = DayStatus(),
    val tue: DayStatus = DayStatus(),
    val wed: DayStatus = DayStatus(),
    val thur: DayStatus = DayStatus(),
    val fri: DayStatus = DayStatus(),
)

data class DayStatus(
    val signedInAdmin: String? = null,
    val signedIn: Boolean = false,
    val signedInTimeStamp: Timestamp? = null,
    val signedOutAdmin: String? = null,
    val signedOut: Boolean = false,
    val signedOutTimeStamp: Timestamp? = null
)

val fakeRoster = mutableListOf(
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
    StudentRoster(
        weekDetails = WeekDetailsDTO()
    ),
)
package com.cedarsstudio.internal.schoollogging.remote.students.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.UUID

data class Student(
    var id: String = UUID.randomUUID().toString(),
    var firstName: String? = null,
    var lastName: String? = null,
    var classSession: String? = null,
    var gender: String? = null,
    var age: String? = null,
    var parent: MutableList<Parent>? = null
) {

    fun isEmpty(): Boolean =
        firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || classSession.isNullOrEmpty() || gender.isNullOrEmpty() || age.isNullOrEmpty()

}

data class Parent(
    var id: String = UUID.randomUUID().toString(),
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var gender: String? = null,
) {
    fun isEmpty() =
        firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || email.isNullOrEmpty() || phone.isNullOrEmpty() || gender.isNullOrEmpty()
}

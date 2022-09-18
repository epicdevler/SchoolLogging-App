package com.cedarsstudio.internal.schoollogging.admins.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox

object Routings {

    const val HOME = "HOME"
    const val ROSTER = "ROSTER"
    const val LIST_OF_STUDENTS = "LIST_OF_STUDENTS"
    const val CHAT_LIST = "CHAT_LIST"
    const val STUDENT_SIGN_IN = "STUDENT_SIGN_IN"
    const val STUDENT_SIGN_OUT = "STUDENT_SIGN_OUT"

    val drawerRoutings = listOf(
        DrawerScreenItem("Student Roster", icon = Icons.Rounded.AccountBox, route
        = ROSTER),
        DrawerScreenItem("List of students", icon = Icons.Rounded.AccountBox, route
        = LIST_OF_STUDENTS),
        DrawerScreenItem("Chat Parent", icon = Icons.Rounded.AccountBox, route
        = CHAT_LIST),
        DrawerScreenItem("Sign in student", icon = Icons.Rounded.AccountBox, route
        = STUDENT_SIGN_IN),
        DrawerScreenItem("Sign out student", icon = Icons.Rounded.AccountBox, route
        = STUDENT_SIGN_OUT)
    )

}
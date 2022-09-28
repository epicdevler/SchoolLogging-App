package com.cedarsstudio.internal.schoollogging.presentations.admins.utils

import com.cedarsstudio.internal.schoollogging.R

/**
 * Contains navigation routes all composable screens of this application
 */
object Routings {

    const val HOME = "HOME"
    const val ROSTER = "ROSTER"
    const val LIST_OF_STUDENTS = "LIST_OF_STUDENTS"
    const val CHAT_LIST = "CHAT_LIST"
    const val CHAT_SCREEN = "CHAT_SCREEN"
    const val AUTH_STUDENT = "AUTH_STUDENT"
    const val AUTH_SCANNER = "AUTH_SCANNER"
    const val STUDENT_PROFILE = "STUDENT_PROFILE"
    const val ADMIN_PROFILE = "ADMIN_PROFILE"
    const val LOGOUT = "LOGOUT"

    val drawerRoutings = listOf(
        DrawerScreenItem("Student Roster", icon = R.drawable.ic_roster, route
        = ROSTER),
        DrawerScreenItem("List of students", icon = R.drawable.ic_list, route
        = LIST_OF_STUDENTS),
        DrawerScreenItem("Chat Parent", icon = R.drawable.ic_chat, route
        = CHAT_LIST),
        DrawerScreenItem("Sign in student", icon = R.drawable.ic_signin, route
        = AUTH_STUDENT),
        DrawerScreenItem("Sign out student", icon = R.drawable.ic_signout, route
        = AUTH_STUDENT)
    )

}
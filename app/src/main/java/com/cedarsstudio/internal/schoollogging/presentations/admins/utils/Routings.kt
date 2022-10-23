package com.cedarsstudio.internal.schoollogging.presentations.admins.utils

import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.AuthType
import com.cedarsstudio.internal.schoollogging.remote.auth.repos.Auth

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
    const val ADD_STUDENT = "ADD_STUDENT"
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
        = AUTH_STUDENT, AuthType.SignIn.name),
        DrawerScreenItem("Sign out student", icon = R.drawable.ic_signout, route
        = AUTH_STUDENT, AuthType.SignOut.name),
        DrawerScreenItem("Add student", icon = R.drawable.ic_add_circled, route
        = ADD_STUDENT)
    )

}
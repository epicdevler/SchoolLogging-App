package com.cedarsstudio.internal.schoollogging.presentations.admins.utils

import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.AuthType

data class DrawerScreenItem(
    val label: String,
    val icon: Int,
    val route: String,
    val extra: String = ""
)
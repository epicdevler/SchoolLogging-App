package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ChatList(onNavigate: (route: String, up: Boolean) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Chat List", modifier = Modifier.constrainAs(placeHolderText) {
            centerTo(parent)
        })
    }
}

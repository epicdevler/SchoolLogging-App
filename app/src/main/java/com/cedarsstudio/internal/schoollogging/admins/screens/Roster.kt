package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Roster(onNavigate: (route: String, up: Boolean) -> Unit,) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Roaster", modifier = Modifier.constrainAs(placeHolderText){
            centerTo(parent)
        })
    }
}

@Composable
fun StudentList(onNavigate: (route: String, up: Boolean) -> Unit,) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Student List", modifier = Modifier.constrainAs(placeHolderText){
            centerTo(parent)
        })
    }
}
@Composable
fun SignInStudent(onNavigate: (route: String, up: Boolean) -> Unit,) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Sign in Student", modifier = Modifier.constrainAs(placeHolderText){
            centerTo(parent)
        })
    }
}

@Composable
fun SignOutStudent(onNavigate: (route: String, up: Boolean) -> Unit,) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Sign out Student", modifier = Modifier.constrainAs(placeHolderText){
            centerTo(parent)
        })
    }
}
@Composable
fun ChatList(onNavigate: (route: String, up: Boolean) -> Unit,) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Chat List", modifier = Modifier.constrainAs(placeHolderText){
            centerTo(parent)
        })
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings

@Composable
fun StudentProfile(
    onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, title, profileImg, profileName, otherInfo, chatParent) = createRefs()
        val startGuide = createGuidelineFromStart(16.dp)
        val endGuide = createGuidelineFromEnd(16.dp)
        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { },
            actions = {},
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })

        Text(text = "Student Information", modifier = Modifier.constrainAs(title) {
            top.linkTo(appbar.bottom, 16.dp)
            centerHorizontallyTo(parent)
        })

        Box(modifier = Modifier
            .size(100.dp)
            .background(color = colorScheme.primary, shape = CircleShape)
            .constrainAs(profileImg) {
                top.linkTo(title.bottom, 24.dp)
                centerHorizontallyTo(title)
            })

        Text(text = "John Doe", style = typography.bodyLarge.copy(color = colorScheme.primary, fontWeight = FontWeight.Bold), modifier = Modifier.constrainAs(profileName) {
            top.linkTo(profileImg.bottom, 10.dp)
            centerHorizontallyTo(profileImg)
        })

        Column(modifier = Modifier.constrainAs(otherInfo) {
            top.linkTo(profileName.bottom, 24.dp)
            start.linkTo(startGuide)
            end.linkTo(endGuide)
            width = Dimension.fillToConstraints
        }) {
            ProfileInfo(label = "Class", value = "JSS1")
            ProfileInfo(label = "Student ID", value = "222043")
            ProfileInfo(label = "Age", value = "16")
            ProfileInfo(label = "Address", value = "Location")
            ProfileInfo(label = "Parent Contact", value = "Number")
        }

        ElevatedButton(onClick = { onNavigate(Routings.CHAT_SCREEN,false)}, colors = ButtonDefaults.elevatedButtonColors(
            containerColor = colorScheme.primary
        ), elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 0.dp, pressedElevation = 0.dp
        ), modifier = Modifier.constrainAs(chatParent) {
            centerHorizontallyTo(otherInfo)
            top.linkTo(otherInfo.bottom, 24.dp)
        }) {
            Text(text = "Chat Parent", color = colorScheme.onPrimary)
        }


    }
}

@Composable
private fun ProfileInfo(
    label: String, value: String
) {
    ElevatedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = label)
            Text(text = value)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
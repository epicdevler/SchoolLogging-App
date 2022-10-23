@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.auth.vm.ProfileVM

@Composable
fun AdminProfile(
    onNavigate: (route: String, up: Boolean) -> Unit,
    profileVm: ProfileVM
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, details) = createRefs()
        val startGuide = createGuidelineFromStart(16.dp)
        val endGuide = createGuidelineFromEnd(16.dp)
        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { Text(text = "Admin Profile") },
            actions = {},
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })

        Column(modifier = Modifier.constrainAs(details) {
            top.linkTo(appbar.bottom, 16.dp)
            start.linkTo(startGuide)
            end.linkTo(endGuide)
            width = Dimension.fillToConstraints
        }) {
            AdminInfo(label = "Username", "${profileVm.admin.value.name}")
            Spacer(modifier = Modifier.height(20.dp))
            AdminInfo(
                label = "Email",
                "${profileVm.admin.value.email}",
                icon = ImageVector.vectorResource(id = R.drawable.mail_outline)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AdminInfo(label = "Password", "1234", Icons.Rounded.Person)
            Spacer(modifier = Modifier.height(20.dp))
            AdminInfo(label = "Admin ID", "${profileVm.admin.value.id}")
        }
    }
}

@Composable
private fun AdminInfo(
    label: String, value: String, icon: ImageVector? = null
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(bottom = 10.dp)
                )
        ) {
            val anBuild = AnnotatedString.Builder()
            anBuild.append(AnnotatedString("$label: "))
            anBuild.append(
                AnnotatedString(
                    value, spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                )
            )

            Text(text = anBuild.toAnnotatedString())
            icon?.let { Icon(imageVector = it, contentDescription = it.name) }
        }
        Divider()
    }
}

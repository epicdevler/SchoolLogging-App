@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn

@Composable
fun AuthStudent(onNavigate: (route: String, up: Boolean) -> Unit) {
    val attempted = rememberSaveable { mutableStateOf(false) }
    val isManual = rememberSaveable { mutableStateOf(false) }
    val authType = AuthType.SignIn
    val studentID = rememberSaveable { mutableStateOf("") }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, inputOption, touch, scanBtn, changeAuthMethod) = createRefs()

        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { },
            actions = {},
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })

        if (!isManual.value) {
//            SHOW UI FOR SCANNING
            QRScanner(onDone = {
                studentID.value = it
            }, modifier = Modifier.constrainAs(inputOption) {
                top.linkTo(appbar.bottom, 24.dp)
                centerHorizontallyTo(parent)
            })
        } else {
            Input(value = studentID.value,
                onValueChange = { studentID.value = it },
                onDone = {},
                modifier = Modifier.constrainAs(inputOption) {
                    top.linkTo(appbar.bottom, 24.dp)
                    centerHorizontallyTo(parent)
                })
        }

        ElevatedButton(onClick = {
            onNavigate(Routings.AUTH_SCANNER, false)
            if (!attempted.value) {
                attempted.value = !attempted.value
            }
        }, colors = ButtonDefaults.elevatedButtonColors(
            contentColor = colorScheme.onPrimary,
            containerColor = colorScheme.primary,
        ), modifier = Modifier.constrainAs(scanBtn) {
            top.linkTo(inputOption.bottom, 24.dp)
            centerHorizontallyTo(inputOption)
        }) {
            if (!isManual.value) {
//                HIDE BUTTON ICON
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_qr),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (!attempted.value) "Scan" else "Scan again",
                    color = colorScheme.onPrimary
                )
            } else Text(
                text = if (authType != AuthType.SignOut) "Sign out" else "Sign in",
                color = colorScheme.onPrimary
            )
        }

        Text(
            text = if (isManual.value) if (authType != AuthType.SignOut) "Sign out with QR Code" else "Sign in with QR Code" else if (authType != AuthType.SignOut) "Sign out with ID" else "Sign in with ID",
            style = typography.labelMedium.copy(
                color = colorScheme.primary, textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures {
                        isManual.value = !isManual.value
                    }
                }
                .constrainAs(changeAuthMethod) {
                    top.linkTo(scanBtn.bottom, 24.dp)
                    centerHorizontallyTo(scanBtn)
                },
        )

    }
}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please input student ID")
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(value = value,
            onValueChange = onValueChange,
            label = { Text(text = "Student ID") },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions { onDone() })
    }
}

@Composable
private fun QRScanner(
    modifier: Modifier = Modifier,
    onDone: (String) -> Unit,
) {

    val isTouchOn = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(20.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_scanner),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        IconToggleButton(
            checked = isTouchOn.value, onCheckedChange = {
                isTouchOn.value = it
            }, colors = IconButtonDefaults.iconToggleButtonColors(
                contentColor = Color.LightGray
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_touch_on),
                contentDescription = null
            )
        }
    }


}


enum class AuthType {
    SignIn, SignOut
}
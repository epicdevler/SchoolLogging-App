@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn

@Composable
fun ChatScreen(
    onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }
) {
    val inputValue = rememberSaveable { mutableStateOf("") }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, chatsList, mediaControls) = createRefs()

        TopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) }, title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_img_one),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "John Doe", style = typography.bodyMedium.copy(colorScheme.onPrimary)
                )
            }
        }, actions = { }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorScheme.primary,
            navigationIconContentColor = colorScheme.onPrimary
        ), modifier = Modifier.constrainAs(appbar) {
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
        })

        LazyColumn(contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
            modifier = Modifier.constrainAs(chatsList) {
                top.linkTo(appbar.bottom)
                bottom.linkTo(mediaControls.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) {
            items(3) { index ->
                ChatMsg(
                    msg = stringResource(R.string.dummy_chat_msg, "$index"),
                    timeStamp = "8:15",
                    index % 2 == 0
                )
            }
        }

        ChatInput(value = inputValue.value,
            onValueChange = { inputValue.value = it },
            onSend = {},
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(mediaControls) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                })

    }
}

@Composable
private fun ChatMsg(
    msg: String, timeStamp: String, isSender: Boolean
) {
    if (isSender) {
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = Modifier
                    .defaultMinSize(40.dp)
                    .fillMaxWidth(.8f)
                    .fillMaxHeight()
                    .background(
                        color = colorScheme.primary,
                        shapes.extraLarge.copy(bottomEnd = CornerSize(0.dp))
                    )
            ) {
                val (_msg, timestamp) = createRefs()
                val startGuide = createGuidelineFromStart(16.dp)
                val endGuide = createGuidelineFromEnd(16.dp)
                val topGuide = createGuidelineFromTop(10.dp)
                val bottomGuide = createGuidelineFromBottom(5.dp)
                Text(
                    text = msg,
                    color = colorScheme.onPrimary,
                    modifier = Modifier.constrainAs(_msg) {
                        top.linkTo(topGuide)
                        start.linkTo(startGuide)
                        end.linkTo(timestamp.start, 3.dp)
                        bottom.linkTo(timestamp.top, 5.dp)
                        width = Dimension.fillToConstraints
                    },
                )
                Text(
                    text = timeStamp,
                    color = colorScheme.onPrimary,
                    modifier = Modifier.constrainAs(timestamp) {
                        top.linkTo(_msg.bottom)
                        end.linkTo(endGuide)
                        bottom.linkTo(bottomGuide)
                    },
                )
            }
        }
    } else {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = Modifier
                    .defaultMinSize(40.dp)
                    .fillMaxWidth(.8f)
                    .fillMaxHeight()
                    .background(
                        color = colorResource(id = R.color.blue_100),
                        shapes.extraLarge.copy(bottomStart = CornerSize(0.dp))
                    )
            ) {
                val (_msg, timestamp) = createRefs()
                val startGuide = createGuidelineFromStart(16.dp)
                val endGuide = createGuidelineFromEnd(16.dp)
                val topGuide = createGuidelineFromTop(10.dp)
                val bottomGuide = createGuidelineFromBottom(5.dp)
                Text(
                    text = msg,
                    modifier = Modifier.constrainAs(_msg) {
                        top.linkTo(topGuide)
                        start.linkTo(timestamp.end, 3.dp)
                        end.linkTo(endGuide)
                        bottom.linkTo(timestamp.top, 5.dp)
                        width = Dimension.fillToConstraints
                    },
                )
                Text(
                    text = timeStamp,
                    modifier = Modifier.constrainAs(timestamp) {
                        top.linkTo(_msg.bottom)
                        start.linkTo(startGuide)
                        bottom.linkTo(bottomGuide)
                    },
                )
            }
        }
    }
}

@Composable
private fun ChatInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    ConstraintLayout(modifier = modifier)
    {
        val (input, sendBtn) = createRefs()
        BasicTextField(
            value = value,
            keyboardActions = KeyboardActions {},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            onValueChange = onValueChange,
            modifier = Modifier
                .background(color = colorResource(id = R.color.blue_50), shape = CircleShape)
                .padding(16.dp)
                .sizeIn(maxWidth = 400.dp)
                .constrainAs(input) {
                    centerVerticallyTo(sendBtn)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(sendBtn.start)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            decorationBox = { innerTextField ->
                innerTextField()
                if (value.isEmpty()) Text(text = "Say something...")
            },
        )
        FilledIconButton(onClick = { /*TODO*/ },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = colorScheme.primary, contentColor = colorScheme.onPrimary
            ),
            modifier = Modifier.constrainAs(sendBtn) {
                end.linkTo(parent.end, 10.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom, 10.dp)
            }) {
            Icon(imageVector = Icons.Rounded.Send, contentDescription = null)
        }
    }
}
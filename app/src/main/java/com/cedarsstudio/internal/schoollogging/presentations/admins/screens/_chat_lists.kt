@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings

@Composable
fun ChatList(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, title, searchICon, chatsList, placeHolderText) = createRefs()

        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { },
            actions = { },
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })

        Text(
            text = "Chats",
            style = typography.titleLarge.copy(
                color = colorScheme.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(appbar.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(searchICon.start, 10.dp)
                width = Dimension.fillToConstraints
            })

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(searchICon) {
            top.linkTo(title.top)
            bottom.linkTo(title.bottom)
            end.linkTo(parent.end)
        }) {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.constrainAs(chatsList) {
                top.linkTo(title.bottom, 19.dp)
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }) {
            items(5) { index ->
                ElevatedCard(onClick = { onNavigate(Routings.CHAT_SCREEN, false) }, shape = RoundedCornerShape(0.dp), elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (img, name, msg, time, count) = createRefs()
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    color = colorScheme.primary,
                                    shape = CircleShape
                                )
                                .size(40.dp)
                                .constrainAs(img) {
                                    start.linkTo(parent.start, 16.dp)
                                    top.linkTo(parent.top, 16.dp)
                                    bottom.linkTo(parent.bottom, 16.dp)
                                }) {
                            Text(
                                text = "D",
                                style = typography.bodyLarge.copy(color = colorScheme.onPrimary)
                            )
                        }
                        Text(
                            text = "Chat ${index + 1}",
                            style = typography.bodyMedium,
                            modifier = Modifier.constrainAs(name) {
                                top.linkTo(img.top)
                                start.linkTo(img.end, 10.dp)
                                bottom.linkTo(msg.top)
                            })
                        Text(
                            text = "Message for Chat ${index + 1}",
                            style = typography.bodySmall,
                            modifier = Modifier.constrainAs(msg) {
                                top.linkTo(name.bottom)
                                start.linkTo(name.start)
                                bottom.linkTo(img.bottom)
                            })
                        Text(
                            text = "${index + 1}mins",
                            style = typography.bodySmall,
                            modifier = Modifier.constrainAs(time) {
                                top.linkTo(img.top)
                                end.linkTo(parent.end, 16.dp)
                                bottom.linkTo(count.top)
                            })
                        Badge(
                            containerColor = colorScheme.primary,
                            modifier = Modifier.constrainAs(count) {
                                top.linkTo(time.bottom)
                                end.linkTo(time.end)
                                bottom.linkTo(img.bottom)
                            }
                        ) {
                            Text(text = "${index + 1}", style = typography.bodySmall.copy(color= colorScheme.onPrimary))
                        }
                    }
                }
            }
        }

    }
}

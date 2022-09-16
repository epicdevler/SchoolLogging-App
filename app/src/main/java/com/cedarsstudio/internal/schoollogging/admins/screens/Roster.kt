@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RosterS(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, categories, studentLists, placeHolderText) = createRefs()

        CenterAlignedTopAppBar(
            navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { Text("Students Roaster") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                }
            },
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            }
        )
        val activeMonth = rememberSaveable {
            mutableStateOf(0)
        }
        LazyRow(
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(categories) {
                top.linkTo(appbar.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }) {
            items(12) { index ->
                val active = index == activeMonth.value
                FilterChip(
                    selected = active,
                    onClick = { activeMonth.value = index },
                    label = {
                        Text(
                            "Month ${index + 1}",
                            color = if (active) Color.White else colorScheme.onBackground
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorScheme.primary,
                        selectedLabelColor = Color.White,
                    )
                )
            }
        }

        Text(
            "Slider",
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                centerHorizontallyTo(parent)
            })

    }
}

@Composable
fun StudentList(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, categories, studentLists, placeHolderText) = createRefs()

        CenterAlignedTopAppBar(
            navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { Text("List of Students") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                }
            },
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            }
        )
        val activeItem = rememberSaveable {
            mutableStateOf(0)
        }
        LazyRow(
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(categories) {
                top.linkTo(appbar.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }) {
            items(100) { index ->
                val active = index == activeItem.value
                FilterChip(
                    selected = active,
                    onClick = { activeItem.value = index },
                    label = {
                        Text(
                            "Class ${index + 1}",
                            color = if (active) Color.White else colorScheme.onBackground
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorScheme.primary,
                        selectedLabelColor = Color.White,
                    )
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                centerHorizontallyTo(parent)
            }) {
            items(20) { index ->
                ElevatedCard(onClick = { /*TODO*/ }) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {

                        Text(
                            text = "Studentsdaf sdfasdf ${index + 1}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(.2f)
                        )
                        Text(text = "Class ${index + 1}")
                        Text(text = "${index + 1}yr${if ((index + 1) != 1) "s" else ""}")
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = null
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = null
                            )

                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SignInStudent(onNavigate: (route: String, up: Boolean) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Sign in Student", modifier = Modifier.constrainAs(placeHolderText) {
            centerTo(parent)
        })
    }
}

@Composable
fun SignOutStudent(onNavigate: (route: String, up: Boolean) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Sign out Student", modifier = Modifier.constrainAs(placeHolderText) {
            centerTo(parent)
        })
    }
}

@Composable
fun ChatList(onNavigate: (route: String, up: Boolean) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (placeHolderText) = createRefs()
        Text(text = "Chat List", modifier = Modifier.constrainAs(placeHolderText) {
            centerTo(parent)
        })
    }
}

@Composable
fun BackBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
    }
}
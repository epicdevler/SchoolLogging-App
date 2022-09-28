@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings

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
                            color = if (active) Color.White else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
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
                ElevatedCard(onClick = { onNavigate(Routings.STUDENT_PROFILE, false)}) {
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
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(.2f)
                        )
                        Text(text = "Class ${index + 1}")
                        Text(text = "${index + 1}yr${if ((index + 1) != 1) "s" else ""}")
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_status_green),
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

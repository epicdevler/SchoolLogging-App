@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.StudentsVM
import com.cedarsstudio.internal.schoollogging.utils.State

@Composable
fun StudentList(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    val vm: StudentsVM = hiltViewModel()
    LaunchedEffect(key1 = Unit){
        vm.getStudents()
    }
    val uiState = vm.uiState.collectAsState().value
    val studentList = vm.studentList.collectAsState().value
    val classes = mutableListOf(
        "All",
        "JSS 1",
        "JSS 2",
        "JSS 3",
        "SS 1",
        "SS 2",
        "SS 3",
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, categories, studentLists, placeHolderText, loader) = createRefs()

        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { Text("List of Students") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                }
            },
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })
        val activeItem = rememberSaveable {
            mutableStateOf(0)
        }
        LazyRow(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(categories) {
                top.linkTo(appbar.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }) {
            items(classes) { item ->
                val active = classes.indexOf(item) == activeItem.value
                FilterChip(selected = active, onClick = {
                    vm.filterList(item, studentList)
                    activeItem.value = classes.indexOf(item)
                }, label = {
                    Text(
                        item,
                        color = if (active) Color.White else MaterialTheme.colorScheme.onBackground
                    )
                }, colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White,
                )
                )
            }
        }

        Text(text = "${uiState.msg}", modifier = Modifier.constrainAs(placeHolderText) {
            top.linkTo(categories.bottom)
            bottom.linkTo(parent.bottom)
            centerHorizontallyTo(parent)
            visibility = if (uiState.state == State.Error) Visibility.Visible else Visibility.Gone
        })

        CircularProgressIndicator(modifier = Modifier.constrainAs(loader) {
            top.linkTo(categories.bottom)
            bottom.linkTo(parent.bottom)
            centerHorizontallyTo(parent)
            visibility = if (uiState.state == State.Loading) Visibility.Visible else Visibility.Gone
        })

        LazyColumn(contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                centerHorizontallyTo(parent)
                visibility =
                    if (uiState.state == State.Success) Visibility.Visible else Visibility.Gone
            }) {
            items(uiState.dataList) { item ->
                ElevatedCard(onClick = { onNavigate(Routings.STUDENT_PROFILE, false) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {

                        Text(
                            text = "${item.firstName} ${item.lastName}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(.2f)
                        )
                        Text(text = "${item.classSession}")
                        Text(text = "${item.age}yrs")
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_status_green),
                            contentDescription = null
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert, contentDescription = null
                            )

                        }
                    }
                }
            }
        }
    }
}

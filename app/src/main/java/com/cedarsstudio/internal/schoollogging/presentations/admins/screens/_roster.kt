@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.StudentsVM
import com.cedarsstudio.internal.schoollogging.remote.students.models.DayStatus
import com.cedarsstudio.internal.schoollogging.remote.students.models.StudentRoster
import com.cedarsstudio.internal.schoollogging.utils.State
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Roster(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    val vm: StudentsVM = hiltViewModel()
    val months = mutableListOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sept",
        "Oct",
        "Nov",
        "Dec",
    )
    LaunchedEffect(key1 = Unit) {
        vm.getRoster()
    }
    val uiState = vm.rosterUiState.collectAsState().value
    val activeItem = rememberSaveable {
        mutableStateOf(0)
    }
    val pagerOne = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

/*    LaunchedEffect(key1 = pagerTwo) {
        snapshotFlow { pagerTwo.currentPage }.collect {
            vm.filterRosterListWeek(it+1, months[pagerOne.currentPage], studentRoster)
        }
    } */


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, categories, studentLists, placeHolderText, loader) = createRefs()
        CenterAlignedTopAppBar(navigationIcon = { BackBtn(onClick = { onNavigate("", true) }) },
            title = { Text("Students Roaster") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                }
            },
            modifier = Modifier.constrainAs(appbar) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            })
        LazyRow(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(categories) {
                top.linkTo(appbar.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }) {
            items(months) { month ->
                val index = months.indexOf(month)
                val active = months.indexOf(month) == activeItem.value
                FilterChip(selected = active, onClick = {
                    activeItem.value = months.indexOf(month)
                    coroutineScope.launch {
                        if (index != pagerOne.currentPage) pagerOne.animateScrollToPage(index)
                    }
                }, label = {
                    Text(
                        month, color = if (active) Color.White else colorScheme.onBackground
                    )
                }, colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colorScheme.primary,
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
        HorizontalPager(
            state = pagerOne,
            count = months.size,
            key = {
                  months[it]
            },
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                centerHorizontallyTo(parent)
                visibility =
                    if (uiState.state == State.Success) Visibility.Visible else Visibility.Gone
            }) { monthIndex ->
            val dataOnThisMonth = uiState.dataList.filter { Log.e("TAG", "Roster: $monthIndex ==> ${it.monthDetail.month}"); it.monthDetail.month == monthIndex }
            Log.e("TAG", "Roster: $monthIndex ==> $dataOnThisMonth")
            Box(Modifier.fillMaxSize()) {
                val pagerTwo = rememberPagerState(0)
                HorizontalPager(
                    count = 4,
                    state = pagerTwo, modifier = Modifier.fillMaxWidth()
                ) { currentWeek ->
//                        if (studentRosterWeek.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(end = 10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listTitleRows(currentWeek + 1)
                            var a = 0
                            while (a < uiState.dataList.size) {
                                listRow(a + 1, uiState.dataList[a])
                                a++
                            }
                        }
                    }
                }
            }
        }

    }
}

fun LazyGridScope.listTitleRows(page: Int) {
    item {}
    item(span = { GridItemSpan(6) }) {
        Text(text = "Week $page", style = typography.labelMedium)
    }
    item {}
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Names", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Start, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Mon", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Tue", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Wed", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Thur", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "Fri", style = typography.bodyLarge.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
            ), modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun LazyGridScope.listRow(index: Int, roster: StudentRoster) {
    item {
        Text(
            text = "$index",
            style = typography.labelSmall.copy(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxSize()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "${roster.studentId}",
            style = typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxSize()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(roster.weekDetails.mon)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(roster.weekDetails.tue)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(roster.weekDetails.wed)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(roster.weekDetails.thur)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(roster.weekDetails.fri)
    }
}

@Composable
private fun Statuss(day: DayStatus) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (day.signedIn) In() else Out()
        Spacer(modifier = Modifier.width(5.dp))
        if (day.signedOut) In() else Out()
    }
}

@Composable
private fun In() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(
                color = colorScheme.primary, shape = shapes.extraSmall.copy(
                    CornerSize(4.dp)
                )
            )
            .padding(4.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(13.dp)
        )
    }
}

@Composable
private fun Out() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                color = colorResource(id = R.color.blue_200).copy(.4f),
                shape = shapes.extraSmall.copy(
                    CornerSize(4.dp)
                )
            )
            .padding(5.dp)
    )
}

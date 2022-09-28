@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.components.BackBtn
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Roster(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
    val pagerOne = rememberPagerState()
    val pagerTwo = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appbar, categories, studentLists, placeHolderText) = createRefs()

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
        val activeMonth = rememberSaveable {
            mutableStateOf(0)
        }
        LazyRow(contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(categories) {
                top.linkTo(appbar.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }) {
            items(12) { index ->
                val active = index == pagerOne.currentPage
                FilterChip(selected = active, onClick = {
                    activeMonth.value = index
                    coroutineScope.launch {
                        if (index != pagerOne.currentPage) pagerOne.animateScrollToPage(index)

                    }
                }, label = {
                    Text(
                        "Month ${index + 1}",
                        color = if (active) Color.White else colorScheme.onBackground
                    )
                }, colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colorScheme.primary,
                    selectedLabelColor = Color.White,
                )
                )
            }
        }

        /*Text(
            "Slider",
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                centerHorizontallyTo(parent)
            })
        
        */

        HorizontalPager(
            state = pagerOne,
            count = 12,
            modifier = Modifier.constrainAs(studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) { firstPage ->

            HorizontalPager(
                state = pagerTwo, count = 4, modifier = Modifier.fillMaxSize()
            ) { page ->
                val state = rememberLazyGridState()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    state = state,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(end = 10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {}
                    item(span = { GridItemSpan(6) }) {
                        Text(text = "Week $firstPage $page", style = typography.labelMedium)
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
                            text = "Mon",
                            style = typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item/*(span = { GridItemSpan(1) })*/ {
                        Text(
                            text = "Tue",
                            style = typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item/*(span = { GridItemSpan(1) })*/ {
                        Text(
                            text = "Wed",
                            style = typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item/*(span = { GridItemSpan(1) })*/ {
                        Text(
                            text = "Thur",
                            style = typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item/*(span = { GridItemSpan(1) })*/ {
                        Text(
                            text = "Fri",
                            style = typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    var a = 1
                    while (a < 10) {
                        listRow(a, "Name $a")
                        a++
                    }
                }
            }
        }

    }
}

private fun LazyGridScope.listRow(index: Int, roster: String) {
    item {
        Text(
            text = "$index",
            style = typography.labelSmall.copy(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxSize()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Text(
            text = "$roster", style = typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxSize()
        )
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(true, false)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(true, true)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(false, true)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(false, false)
    }
    item/*(span = { GridItemSpan(1) })*/ {
        Statuss(false, false)
    }
}

@Composable
private fun Statuss(_in: Boolean, _out: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (_in) In() else Out()
        Spacer(modifier = Modifier.width(5.dp))
        if (_out) In() else Out()
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

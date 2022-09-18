@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.admins.screens.components.BackBtn

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Roster(onNavigate: (route: String, up: Boolean) -> Unit = { _, _ -> }) {
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

        val state = rememberLazyGridState()

        LazyVerticalGrid(columns = GridCells.Fixed(6), state = state, verticalArrangement =
        Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(end = 10.dp), modifier =
        Modifier
            .constrainAs
                (studentLists) {
                top.linkTo(categories.bottom, 10.dp)
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) {
            item(span = { GridItemSpan(6) }) {
                Text(text = "Week 1", style = typography.labelMedium)
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Names", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Mon", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Tue", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Wed", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Thur", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Fri", textAlign = TextAlign.Center, style = typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Name", style = typography.bodyMedium,
                    modifier = Modifier.fillMaxSize())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }

            item/*(span = { GridItemSpan(1) })*/ {
                Text(text = "Name", style = typography.bodyMedium,
                    modifier = Modifier.fillMaxSize())
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
            item/*(span = { GridItemSpan(1) })*/ {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment
                    .CenterVertically, modifier = Modifier.fillMaxWidth().background(color =
                Color.Red)) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
//                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            }
        }

    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.admins.utils.DrawerScreenItem
import com.cedarsstudio.internal.schoollogging.admins.utils.Routings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Home(
    onNavigate: (route: String, up: Boolean) -> Unit
) {
    val drawerScreenItems = Routings.drawerRoutings
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(drawerState, coroutineScope, drawerScreenItems) { route ->
                coroutineScope.launch {
                    if(drawerState.isOpen) { drawerState.close()  }
                }
            }
        },
        drawerState = drawerState
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize())
        {
            val (appbar, welcomeText, username, gridItems) = createRefs()
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.constrainAs(appbar){
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
            )
            Text("Welcome",
                modifier = Modifier.constrainAs(welcomeText) {
                    top.linkTo(appbar.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                })
            Text("John Doe",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .constrainAs
                        (username) {
                        top.linkTo(welcomeText.bottom)
                        start.linkTo(parent.start, 16.dp)
                    })

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.constrainAs(gridItems) {
                    top.linkTo(username.bottom, 22.dp)
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                items(drawerScreenItems) { item ->
                    HomeCardItem(onClick = { onNavigate(item.route, false) }, icon = item.icon,
                        label = item.label)
                }
            }
        }
    }
}
@Composable
private fun DrawerContent(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    drawerScreenItems: List<DrawerScreenItem>,
    onNavigate: (route: String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                )
            )
    ) {
        val (bckBtn, profileBtn, itemsList, logoutBtn) = createRefs()

        val startGuide = createGuidelineFromStart(16.dp)
        val endGuide = createGuidelineFromEnd(16.dp)
        val bottomGuide = createGuidelineFromBottom(20.dp)

        IconButton(onClick = {
            coroutineScope.launch {
                if (drawerState.isOpen) drawerState.close()
            }
        }, modifier = Modifier.constrainAs(bckBtn) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) {
            Icon(painter = painterResource(id = R.drawable.back_arrow), contentDescription =
            null, tint = Color.White)
        }

        LazyColumn(modifier = Modifier.constrainAs(itemsList) {
            top.linkTo(bckBtn.bottom, 22.dp)
            bottom.linkTo(logoutBtn.top)
            start.linkTo(startGuide)
//                end.linkTo(endGuide)
            height = Dimension.fillToConstraints
        }) {
            items(drawerScreenItems) { item ->
                DrawerItem(
                    onClick = { onNavigate(item.route) },
                    active = false,
                    icon = item.icon,
                    label = item.label
                )
            }
        }

        DrawerItem(
            onClick = { /*TODO*/ },
            active = true,
            icon = Icons.Rounded.Close,
            label = "Logout",
            modifier = Modifier.constrainAs(logoutBtn) {
                start.linkTo(startGuide)
                bottom.linkTo(bottomGuide)
            }
        )


    }
}

@Composable
private fun DrawerItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    active: Boolean,
    icon: ImageVector,
    label: String,
) {
    ElevatedButton(onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = if (active) Color.Gray else Color.Transparent,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            0.dp, 0.dp, 0.dp, 0.dp
        ),
        modifier =
        modifier) {
        Icon(imageVector = icon, contentDescription = icon.name)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = label, color = MaterialTheme.colorScheme.onPrimary)
    }
}


@Composable
private fun HomeCardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
) {
    ElevatedCard(onClick = onClick, colors = CardDefaults.elevatedCardColors(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primary
    ), shape = MaterialTheme.shapes.small, modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(18.dp, 22.dp))) {
            Icon(imageVector = icon, contentDescription = icon.name, modifier = Modifier.size
                (34.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
package com.cedarsstudio.internal.schoollogging.admins.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.admins.utils.DrawerScreen
import com.cedarsstudio.internal.schoollogging.admins.utils.Routings
import com.cedarsstudio.internal.schoollogging.databinding.ActivityMainBinding
import com.google.android.material.composethemeadapter3.Mdc3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.apply {
            setContent {
                Mdc3Theme(setTextColors = true) {
                    Main()
                }
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun Main() {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        val drawerScreenItems = Routings.drawerRoutings
        ModalNavigationDrawer(
            drawerContent = {
                DrawerContent(drawerState, coroutineScope, drawerScreenItems) { route ->
                    navController.navigate(route)
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
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
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.padding(PaddingValues(top = it.calculateTopPadding()))) {
                    NavHost(navController = navController, startDestination = Routings.HOME) {
                        composable(Routings.HOME) {
                            Home(onNavigate = { route, up ->
                                if (up) navController.navigateUp()
                                else navController.navigate(route)
                            }, navController, drawerScreenItems =
                            drawerScreenItems)
                        }
                        composable(Routings.ROSTER) {
                            Roster(
                                onNavigate = { route, up ->
                                    if (up) navController.navigateUp()
                                    else navController.navigate(route)
                                },
                            )
                        }
                        composable(Routings.LIST_OF_STUDENTS) {
                            StudentList(
                                onNavigate = { route, up ->
                                    if (up) navController.navigateUp()
                                    else navController.navigate(route)
                                },
                            )
                        }
                        composable(Routings.CHAT_LIST) {
                            ChatList(
                                onNavigate = { route, up ->
                                    if (up) navController.navigateUp()
                                    else navController.navigate(route)
                                },
                            )
                        }
                        composable(Routings.STUDENT_SIGN_IN) {
                            SignInStudent(
                                onNavigate = { route, up ->
                                    if (up) navController.navigateUp()
                                    else navController.navigate(route)
                                },
                            )
                        }
                        composable(Routings.STUDENT_SIGN_OUT) {
                            SignOutStudent(
                                onNavigate = { route, up ->
                                    if (up) navController.navigateUp()
                                    else navController.navigate(route)
                                },
                            )
                        }
                    }
                }
            }

        }
    }

    @Composable
    private fun DrawerContent(
        drawerState: DrawerState,
        coroutineScope: CoroutineScope,
        drawerScreenItems: List<DrawerScreen>,
        onNavigate: (route: String) -> Unit,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight()
                .background(
                    color = colorScheme.primary,
                    shape = shapes.medium.copy(
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
            shape = shapes.small,
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
            Text(text = label)
        }
    }
}


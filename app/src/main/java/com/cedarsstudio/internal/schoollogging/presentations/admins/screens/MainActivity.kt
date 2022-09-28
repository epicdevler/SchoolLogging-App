package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.cedarsstudio.internal.schoollogging.databinding.ActivityMainBinding
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings
import com.cedarsstudio.internal.schoollogging.utils.toAuthScreen
import com.google.android.material.composethemeadapter3.Mdc3Theme

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
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(PaddingValues(top = it.calculateTopPadding()))) {
                NavHost(navController = navController, startDestination = Routings.HOME) {
                    composable(Routings.HOME) {
                        Home(onNavigate = { route, up -> handleOnNavUp(navController, route, up) })
//                        CameraTest()

                    }
                    composable(Routings.ROSTER) {
                        Roster(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.LIST_OF_STUDENTS) {
                        StudentList(onNavigate = { route, up ->
                            handleOnNavUp(navController, route, up)
                        })
                    }
                    composable(Routings.CHAT_LIST) {
                        ChatList(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.CHAT_SCREEN) {
                        ChatScreen(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.AUTH_STUDENT) {
                        AuthStudent(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.AUTH_SCANNER) {
                        CameraTest(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.STUDENT_PROFILE) {
                        StudentProfile(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                    composable(Routings.ADMIN_PROFILE) {
                        AdminProfile(
                            onNavigate = { route, up ->
                                handleOnNavUp(navController, route, up)
                            },
                        )
                    }
                }
            }
        }
    }

    private fun handleOnNavUp(navController: NavHostController, route: String, up: Boolean) {
        if (up) navController.navigateUp()
        else if (route.equals(Routings.LOGOUT, true))
            toAuthScreen(/*true*/)
        else navController.navigate(route)
    }
}


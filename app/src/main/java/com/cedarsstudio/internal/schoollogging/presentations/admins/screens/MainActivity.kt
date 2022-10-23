package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cedarsstudio.internal.schoollogging.databinding.ActivityMainBinding
import com.cedarsstudio.internal.schoollogging.presentations.admins.utils.Routings
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.AuthStudentVM
import com.cedarsstudio.internal.schoollogging.presentations.auth.screens.SignIn
import com.cedarsstudio.internal.schoollogging.presentations.auth.vm.ProfileVM
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.cedarsstudio.internal.schoollogging.utils.*
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val profileVm: ProfileVM by viewModels()
    private val authStudentVM: AuthStudentVM by viewModels()
    private lateinit var navController: NavHostController
    private lateinit var responseModal: ResponseModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        responseModal = ResponseModal(this)
        setContentView(binding.root)

        binding.composeView.apply {
            setContent {
                Mdc3Theme(setTextColors = true) {
                    Main()
                }
            }
        }
        initObservers()
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun Main() {
        navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(PaddingValues(top = it.calculateTopPadding()))) {
                NavHost(navController = navController, startDestination = Routings.HOME) {
                    composable(Routings.HOME) {
                        Home(
                            onNavigate = { route, up -> handleOnNavUp(route, up) },
                            profileVm,
                        )
//                        CameraTest()
                    }
                    composable(Routings.ROSTER) {
                        Roster(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            },
                        )
                    }
                    composable(Routings.LIST_OF_STUDENTS) {
                        StudentList(onNavigate = { route, up ->
                            handleOnNavUp(route, up)
                        })
                    }
                    composable(Routings.CHAT_LIST) {
                        ChatList(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            },
                        )
                    }
                    composable(Routings.CHAT_SCREEN) {
                        ChatScreen(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            },
                        )
                    }
                    composable(Routings.AUTH_STUDENT) {
                        AuthStudent(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            }, authStudentVM
                        )
                    }
                    composable(Routings.AUTH_SCANNER) {
                        CameraTest(
                            context = LocalContext.current,
                            authStudentVM,
                            onNavigate = { up ->
                                handleOnNavUp(up = up)
                            })
                    }
                    composable(Routings.STUDENT_PROFILE) {
                        StudentProfile(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            },
                        )
                    }
                    composable(Routings.ADMIN_PROFILE) {
                        AdminProfile(
                            onNavigate = { route, up ->
                                handleOnNavUp(route, up)
                            }, profileVm
                        )
                    }
                }
            }
        }
    }


    private fun initObservers() {

        lifecycleScope.launch {
            authStudentVM.uiState.collect { uiState ->
                Log.e(SignIn.TAG, "initObservers: $uiState")
                if (uiState.state == State.Loading) {
                    responseModal.showLoader("${uiState.msg}", false)
                } else {
                    responseModal.dismiss()
                }
                when (uiState.state) {
                    State.Success -> {
                        responseModal.show(true, "${uiState.msg}", false) {
                            responseModal.dismiss()
                        }
                    }
                    State.Error -> {
                        responseModal.show(msg = "${uiState.msg}", isCancellable = true)
                    }
                    else -> {}
                }
            }
        }

    }

    private fun handleOnNavUp(_route: String = "", up: Boolean) {
        if (up) navController.navigateUp()
        else if (_route.equals(Routings.LOGOUT, true)) {
            lifecycleScope.launch {
                if (profileVm.signOut() is Response.Success) {
                    toAuthScreen(true)
                }
            }
        } else if (_route.equals(Routings.ADD_STUDENT, true)) toAdminAddStudentScreen()
//        else if (_route.equals(Routings.ROSTER, true)) toRoster()
        else {
            if (_route.contains("/")) {
                val extra = _route.split("/")[1]
                val route = _route.split("/")[0]
                authStudentVM.setAuthType(extra)
                navController.navigate(route)
            } else navController.navigate(_route)
        }
    }
}


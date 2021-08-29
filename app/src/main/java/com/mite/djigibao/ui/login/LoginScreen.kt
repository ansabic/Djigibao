package com.mite.djigibao.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mite.djigibao.Destination
import com.mite.djigibao.model.Role
import com.mite.djigibao.ui.*
import com.mite.djigibao.ui.theme.Purple200
import javax.inject.Inject

class LoginScreen @Inject constructor() {
    @ExperimentalAnimationApi
    @Composable
    fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
        viewModel.findIfLoggedInAlready()
        viewModel.syncUsers()
        val loggedIn = viewModel.alreadyLoggedIn.observeAsState()
        val pickingRole = viewModel.pickedRole.observeAsState(Role.NONE)
        val isPicking = viewModel.isPickingRole.observeAsState()
        val warning = viewModel.warning.observeAsState(false)
        val navigateFlag = viewModel.loadingToMainScreen.observeAsState()
        if (loggedIn.value == true) {
            navController.navigate(Destination.MainScreen.name)
        }
        if (navigateFlag.value == true) {
            navController.navigate(Destination.MainScreen.name)
            viewModel.leftScreen()
        }
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Purple200),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    DjigibaoTitle()
                    Spacer(
                        modifier = Modifier
                            .padding(top = 100.dp)
                    )
                    UsernameInput {
                        viewModel.usernameUpdate(it)
                    }
                    Spacer(
                        Modifier
                            .padding(top = 10.dp)
                    )
                    PasswordInput {
                        viewModel.passwordUpdate(it)
                    }
                    Spacer(
                        Modifier
                            .padding(top = 10.dp)
                    )
                    if (loggedIn.value == false) {
                        Row(
                            modifier = Modifier
                                .width(300.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SimpleText("Pick a main role: ")
                            Spacer(
                                Modifier
                                    .padding(horizontal = 10.dp),
                            )
                            if (isPicking.value == true) {
                                DjigibaoRoleDropdown(
                                    isPicking = isPicking.value == true,
                                    selectedItem = {
                                        viewModel.pickRole(it)
                                    },
                                    onDismiss = {
                                        viewModel.isPicking(false)
                                    })
                            }

                            DjigibaoButtonStretch(pickingRole.value.text) {
                                viewModel.isPicking(true)

                            }
                            Spacer(
                                Modifier
                                    .padding(top = 100.dp)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .width(300.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (loggedIn.value == false)
                            DjigibaoButtonWrap("Register") {
                                viewModel.register()
                            }
                        else
                            DjigibaoButtonWrap("Login") {
                                viewModel.logIn()
                            }
                    }


                }

            }
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    warning.value
                ) {
                    Snackbar {
                        Text(
                            text = "You must fill all fields to continue!",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }


    }
}
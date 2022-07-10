package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.shared.Status.*
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.screens.login.LoginScreen
import com.example.giftcardshop.view.viewmodels.LoginViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.loginComposable(
    loginViewModel: LoginViewModel,
    navigate: () -> Unit
) {
    this.composable(route = Constants.LOGIN_SCREEN) {
        val signInStatus by loginViewModel.signInStatus.collectAsState()
        val attempts = remember { mutableStateOf(0) }
        val isError = attempts.value > 0

        when (signInStatus.status) {
            SUCCESS -> {
                if (signInStatus.data == false) {

                    LoginScreen(
                        error = isError,
                        onLoginClick = { username, password ->
                            loginViewModel.signIn(username, password)
                            attempts.value ++
                        }
                    )
                } else if (signInStatus.data == true) {
                    attempts.value = 0
                    navigate()
                }
            }
            LOADING -> ProgressBar() // TODO: Could be better
            ERROR -> {
                // TODO: Error Screen with Reload Button
            }
            else -> {}
        }
    }
}
package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.screens.login.LoginScreen
import com.example.giftcardshop.view.viewmodels.LoginViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.loginComposable(
    loginViewModel: LoginViewModel,
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.LOGIN_SCREEN
    ) {
        val signInStatus by loginViewModel.signInStatus.collectAsState().also {
            println("DEBUG_SAI: SIGN_IN_DATA > ${it.value}")
        }
//        if (dataStoreAuth.status == Status.SUCCESS) {
//            if (dataStoreAuth.data?.authStatus == true) {
//                loginViewModel.signIn(
//                    dataStoreAuth.data?.username!!,
//                    dataStoreAuth.data?.password!!
//                )
//            }
//        }

        when (signInStatus.status) {
            Status.SUCCESS -> {
                if (signInStatus.data == false) {
                    LoginScreen(onLoginClick = { username, password ->
                        loginViewModel.signIn(username, password)
                    })
                } else if (signInStatus.data == true) {
                    navigate.invoke()
                }
            }
            Status.LOADING -> ProgressBar()
            Status.ERROR -> {
                // TODO: HOW TO PRESENT THE ERROR?
            }
            else -> {

            }
        }
    }
}
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
    navigateToList: () -> Unit
) {
    this.composable(route = Constants.LOGIN_SCREEN) {

        LoginScreen(
            loginViewModel = loginViewModel,
            navigateToList = navigateToList
        )
    }
}
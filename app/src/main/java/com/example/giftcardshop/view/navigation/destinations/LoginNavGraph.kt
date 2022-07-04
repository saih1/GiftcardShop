package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.TestGenericComposable
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.loginComposable(
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.LOGIN_SCREEN
    ) {
        TestGenericComposable(
            text = "Login",
            navigate = navigate
        )
    }
}
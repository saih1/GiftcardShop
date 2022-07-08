package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.SPLASH_SCREEN,
        enterTransition = { slideInHorizontally(animationSpec = tween(300)) },
        exitTransition = { slideOutVertically(animationSpec = tween(300)) }
    ) {
        SplashScreen(navigateToList = navigate)
    }
}
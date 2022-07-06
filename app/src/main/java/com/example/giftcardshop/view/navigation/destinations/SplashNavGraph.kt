package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.R.*
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.TestGenericComposable
import com.example.giftcardshop.view.screens.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.SPLASH_SCREEN,
        enterTransition = { slideInHorizontally(animationSpec = tween(300)) },
        exitTransition = { slideOutVertically(animationSpec = tween(300)) }
    ) {
        SplashScreen(navigate = navigate)
    }
}
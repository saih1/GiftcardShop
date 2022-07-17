package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.ProgressBar
import com.google.accompanist.navigation.animation.composable
import com.example.giftcardshop.view.screens.list.GiftcardList
import com.example.giftcardshop.view.screens.list.GiftcardListScreen
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.listComposable(
    giftcardViewModel: GiftcardViewModel,
    loginViewModel: LoginViewModel,
    navigateToDetail: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    this.composable(
        route = Constants.LIST_SCREEN,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(500),
                towards = AnimatedContentScope.SlideDirection.Up)
        },
        exitTransition = {
            this.slideOutOfContainer(
                animationSpec = tween(500),
                towards = AnimatedContentScope.SlideDirection.Down
            )
        }
    ) {
        GiftcardListScreen(
            giftcardViewModel = giftcardViewModel,
            loginViewModel = loginViewModel,
            navigateToCart = navigateToCart,
            navigateToLogin = navigateToLogin,
            navigateToDetail = navigateToDetail
        )
    }
}
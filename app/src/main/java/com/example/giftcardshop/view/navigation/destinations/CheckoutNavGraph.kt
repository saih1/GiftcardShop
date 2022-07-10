package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.checkout.CheckoutScreen
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.checkoutComposable(
    checkoutViewModel: CheckoutViewModel,
    navigateToList: () -> Unit
) {
    this.composable(
        route = Constants.CHECKOUT_SCREEN
    ) {
        CheckoutScreen(
            checkoutViewModel = checkoutViewModel,
            navigateToList = navigateToList
        )
    }
}
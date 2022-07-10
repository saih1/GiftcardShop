package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.cart.CartScreen
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cartComposable(
    cartViewModel: CartViewModel,
    checkoutViewModel: CheckoutViewModel,
    navigateToCheckout: () -> Unit,
    navigateToList: () -> Unit
) {
    this.composable(
        route = Constants.CART_SCREEN,
    ) {
        CartScreen(
            cartViewModel = cartViewModel,
            checkoutViewModel = checkoutViewModel,
            navigateToCheckout = navigateToCheckout,
            navigateToList = navigateToList
        )
    }
}
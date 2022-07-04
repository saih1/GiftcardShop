package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.cart.CartScreen
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cartComposable(
    cartViewModel: CartViewModel,
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.CART_SCREEN,
    ) {
        val cartItems by cartViewModel.cartItems.collectAsState()
        CartScreen(
            cartItems = cartItems.data ?: emptyList(),
            onDeleteItem = {
                cartViewModel.deleteFromCart(it)
            },
            navigate = navigate
        )
    }
}
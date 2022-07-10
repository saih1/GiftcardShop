package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.cart.CartScreen
import com.example.giftcardshop.view.screens.cart.EmptyCartScreen
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cartComposable(
    cartViewModel: CartViewModel,
    checkoutViewModel: CheckoutViewModel,
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.CART_SCREEN,
    ) {
        val cartItems: RequestState<List<CartItem>> by cartViewModel.cartItems.collectAsState()

        when (cartItems.status) {
            Status.SUCCESS -> {
                if (cartItems.data?.isEmpty() == true) {
                    EmptyCartScreen()
                } else {
                    CartScreen(
                        cartItems = cartItems.data ?: emptyList(),
                        onDeleteItemClick = {
                            cartViewModel.deleteFromCart(it) },
                        onCheckoutClick = {
                            checkoutViewModel.requestCheckout(it)
                            navigate.invoke() },
                        onClearCartClick = {
                            cartViewModel.clearCart()
                        }
                    )
                }
            }
        }
    }
}
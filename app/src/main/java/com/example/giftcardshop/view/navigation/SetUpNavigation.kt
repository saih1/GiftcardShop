@file:OptIn(ExperimentalAnimationApi::class)

package com.example.giftcardshop.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.giftcardshop.shared.Constants.CART_SCREEN
import com.example.giftcardshop.shared.Constants.CHECKOUT_SCREEN
import com.example.giftcardshop.shared.Constants.DETAIL_SCREEN
import com.example.giftcardshop.shared.Constants.LIST_SCREEN
import com.example.giftcardshop.shared.Constants.LOGIN_SCREEN
import com.example.giftcardshop.view.navigation.destinations.*
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavigation(navController: NavHostController) {

    val giftcardViewModel: GiftcardViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val checkoutViewModel: CheckoutViewModel = viewModel()

    AnimatedNavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN
    ) {
        loginComposable(
            loginViewModel = loginViewModel,
            navigateToList = { navController.navigate(LIST_SCREEN) }
        )
        listComposable(
            giftcardViewModel = giftcardViewModel,
            loginViewModel = loginViewModel,
            navigateToDetail = { navController.navigate(DETAIL_SCREEN) },
            navigateToCart = { navController.navigate(CART_SCREEN) },
            navigateToLogin = { navController.navigate(LOGIN_SCREEN) }
        )
        detailComposable(
            giftcardViewModel = giftcardViewModel,
            checkoutViewModel = checkoutViewModel,
            navigateToList = { navController.navigate(LIST_SCREEN) },
            navigateToCheckout = { navController.navigate(CHECKOUT_SCREEN) }
        )
        cartComposable(
            cartViewModel = cartViewModel,
            checkoutViewModel = checkoutViewModel,
            navigateToCheckout = { navController.navigate(CHECKOUT_SCREEN) },
            navigateToList = { navController.navigate(LIST_SCREEN) }
        )
        checkoutComposable(
            checkoutViewModel = checkoutViewModel,
            navigateToList = { navController.navigate(LIST_SCREEN) {
                popUpTo(CHECKOUT_SCREEN) { inclusive = true }
            } }
        )
    }
}
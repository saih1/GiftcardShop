@file:OptIn(ExperimentalAnimationApi::class)

package com.example.giftcardshop.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.giftcardshop.shared.Constants.CART_SCREEN
import com.example.giftcardshop.shared.Constants.CHECKOUT_SCREEN
import com.example.giftcardshop.shared.Constants.DETAIL_SCREEN
import com.example.giftcardshop.shared.Constants.LIST_SCREEN
import com.example.giftcardshop.shared.Constants.LOGIN_SCREEN
import com.example.giftcardshop.shared.Constants.SPLASH_SCREEN
import com.example.giftcardshop.view.navigation.destinations.*
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
fun SetUpNavigation(
    navController: NavHostController,
    giftcardViewModel: GiftcardViewModel,
    cartViewModel: CartViewModel,
    checkoutViewModel: CheckoutViewModel,
    loginViewModel: LoginViewModel
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN
    ) {
        loginComposable(
            loginViewModel = loginViewModel,
            navigate = { navController.navigate(SPLASH_SCREEN) }
        )
        splashComposable(
            navigate = {
                navController.navigate(LIST_SCREEN) {
                popUpTo(LIST_SCREEN) { inclusive = true } }
            }
        )
        listComposable(
            giftcardViewModel = giftcardViewModel,
            navigate = { navController.navigate(DETAIL_SCREEN) },
            navigateToCart = {
                navController.navigate(CART_SCREEN)
            },
            onSignOutClick = {
                loginViewModel.signOut()
                navController.navigate(LOGIN_SCREEN)
            }
        )
        detailComposable(
            giftcardViewModel = giftcardViewModel,
//            navigate = { navController.navigate(CART_SCREEN) },
            navigate = {
//                navController.navigate(CART_SCREEN)
            }
        )
        cartComposable(
            cartViewModel = cartViewModel,
            checkoutViewModel = checkoutViewModel,
            navigate = { navController.navigate(CHECKOUT_SCREEN) }
        )
        checkoutComposable(
            checkoutViewModel = checkoutViewModel,
            navigate = {
                navController.navigate(LIST_SCREEN) {
                    popUpTo(CHECKOUT_SCREEN) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
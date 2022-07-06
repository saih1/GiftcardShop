@file:OptIn(ExperimentalAnimationApi::class)

package com.example.giftcardshop.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.giftcardshop.domain.model.Giftcard
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
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay

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
            navigate = { navController.navigate(LIST_SCREEN) }
        )
    }
}
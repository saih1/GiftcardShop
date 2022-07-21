package com.example.giftcardshop.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.example.giftcardshop.view.navigation.SetUpNavigation
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val giftcardViewModel: GiftcardViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val checkoutViewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiftcardShopTheme {
                navController = rememberAnimatedNavController()
                SetUpNavigation(
                    navController = navController,
                    giftcardViewModel = giftcardViewModel,
                    cartViewModel = cartViewModel,
                    loginViewModel = loginViewModel,
                    checkoutViewModel = checkoutViewModel,
                )
            }
        }
    }
}
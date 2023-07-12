package com.example.giftcardshop.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.example.giftcardshop.view.navigation.SetUpNavigation
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiftcardShopTheme {
                navController = rememberAnimatedNavController()
                SetUpNavigation(navController = navController)
            }
        }
    }
}
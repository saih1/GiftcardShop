package com.example.giftcardshop.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.view.cart_view.CartViewModel
import com.example.giftcardshop.view.giftcard_view.GiftcardViewModel
import com.example.giftcardshop.view.navigation.SetUpNavigation
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val giftcardViewModel: GiftcardViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiftcardShopTheme {
                navController = rememberAnimatedNavController()
                SetUpNavigation(
                    navController = navController,
                    giftcardViewModel = giftcardViewModel,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
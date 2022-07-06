package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.ProgressBar
import com.google.accompanist.navigation.animation.composable
import com.example.giftcardshop.view.screens.list.GiftcardListScreen
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.listComposable(
    giftcardViewModel: GiftcardViewModel,
    navigate: () -> Unit,
    navigateToCart: () -> Unit,
) {
    this.composable(
        route = Constants.LIST_SCREEN
    ) {
        val giftcards by giftcardViewModel.giftcards.collectAsState()
        when (giftcards.status) {
            Status.LOADING -> ProgressBar()
            Status.SUCCESS -> GiftcardListScreen(
                giftcards = giftcards.data ?: emptyList(),
                onItemSelect = { giftcardViewModel.selectGiftcard(it) },
                navigate = navigate,
                navigateToCart = navigateToCart
            )
            Status.ERROR -> Text(text = "ERROR!")
        }
    }
}
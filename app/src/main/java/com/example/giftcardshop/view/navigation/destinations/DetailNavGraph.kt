package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.detail.GiftcardDetailScreen
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.detailComposable(
    giftcardViewModel: GiftcardViewModel,
    navigate: () -> Unit
) {
    this.composable(
        route = Constants.DETAIL_SCREEN
    ) {
        val selectedGiftcard: Giftcard? by giftcardViewModel.selectedGiftcard.collectAsState()

        GiftcardDetailScreen(
            giftcard = selectedGiftcard!!,
            onBuyNowClick = { giftcard, value ->
                // TODO: Navigate to confirmation
            },
            onAddToCartClick = { giftcard, value ->
                giftcardViewModel.addToCart(giftcard, value)
            },
            navigate = navigate
        )
    }
}
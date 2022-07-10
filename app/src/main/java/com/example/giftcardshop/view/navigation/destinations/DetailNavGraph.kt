package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.view.screens.detail.GiftCardDetailScreen
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.detailComposable(
    giftcardViewModel: GiftcardViewModel,
    checkoutViewModel: CheckoutViewModel,
    navigateToList: () -> Unit,
    navigateToCheckout: () -> Unit,
) {
    this.composable(
        route = Constants.DETAIL_SCREEN
    ) {
        GiftCardDetailScreen(
            giftcardViewModel = giftcardViewModel,
            checkoutViewModel = checkoutViewModel,
            navigateToCheckout = navigateToCheckout,
            navigateToList = navigateToList
        )
    }
}
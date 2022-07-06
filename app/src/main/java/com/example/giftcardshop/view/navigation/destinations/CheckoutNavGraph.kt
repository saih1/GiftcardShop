package com.example.giftcardshop.view.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.example.giftcardshop.shared.Constants
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.screens.TestGenericComposable
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.checkoutComposable(
    navigate: () -> Unit,
    checkoutViewModel: CheckoutViewModel
) {
    this.composable(
        route = Constants.CHECKOUT_SCREEN
    ) {
        val checkoutStatus by checkoutViewModel.checkoutStatus.collectAsState()

        when (checkoutStatus.status) {
            Status.LOADING -> ProgressBar()
            Status.SUCCESS -> {
                if (checkoutStatus.data == false) {
                    TestGenericComposable(
                        text = "Failed",
                        navigate = navigate
                    )
                } else {
                    TestGenericComposable(
                        text = "Checkout Confirmation",
                        navigate = navigate
                    )
                }
            }
            Status.ERROR -> TestGenericComposable(
                text = "Error!",
                navigate = navigate
            )
            else -> {

            }
        }
    }
}
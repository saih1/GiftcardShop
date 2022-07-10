@file:OptIn(ExperimentalMaterialApi::class)

package com.example.giftcardshop.view.screens.detail

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.data.network.dto.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.discountPercentage
import com.example.giftcardshop.shared.toStringFromHTML
import com.example.giftcardshop.view.screens.AsyncImageBox
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import io.dokar.expandabletext.ExpandableText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GiftCardDetailScreen(
    giftcardViewModel: GiftcardViewModel,
    checkoutViewModel: CheckoutViewModel,
    navigateToCheckout: () -> Unit,
    navigateToList: () -> Unit
) {
    val selectedGiftcard: Giftcard? by giftcardViewModel.selectedGiftcard.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { /* TODO ADD CUSTOM TOP BAR HERE (NAV2List) */ },
    ) {
        GiftcardDetailComposable(
            giftcard = selectedGiftcard!!,
            onBuyNowClick = { giftcard, value ->
                checkoutViewModel.requestBuyNow(value)
                navigateToCheckout()
            },
            onAddToCartClick = { giftcard, value ->
                giftcardViewModel.addToCart(giftcard, value)
            },
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun GiftcardDetailComposable(
    giftcard: Giftcard,
    onBuyNowClick: (Giftcard, Double) -> Unit,
    onAddToCartClick: (Giftcard, Double) -> Unit,
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                enabled = true,
                state = scrollState
            ),
    ) {
        var expended: Boolean by remember { mutableStateOf(false) }
        var expandedText by remember { mutableStateOf(false) }
        var selectedValue: Double by remember {
            mutableStateOf(giftcard.denominations.first().price)
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(10.dp)
        ) {
            AsyncImageBox(
                imageUrl = giftcard.image,
                imageWidth = 400.dp,
                imageHeight = 220.dp
            )

            Text(
                text = giftcard.brand,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )

            Text(
                text = "Terms",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(5.dp)
                ,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )

            ExpandableText(
                expanded = expandedText,
                text = giftcard.terms.toStringFromHTML(),
                modifier = Modifier
                    .animateContentSize(tween(500))
                    .clickable { expandedText = !expandedText }
                    .padding(5.dp),
                maxLines = 4,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                fontSize = 13.sp,
            )

            Button(onClick = {
                expended = true
            }) {
                Text(text = "Select Value")
                DropdownMenu(
                    expanded = expended,
                    onDismissRequest = { expended = false },
                    modifier = Modifier.size(150.dp)
                ) {
                    giftcard.denominations.forEach {
                        DropdownMenuItem(
                            onClick = {
                                expended = false
                                selectedValue = it.price
                            },
                        ) {
                            DenominationItem(denomination = it)
                        }
                    }
                }
            }
            Button(
                onClick = {
                    onBuyNowClick(giftcard, selectedValue)
                }
            ) {
                Text(text = "Buy Now")
            }
            Button(
                onClick = {
                    onAddToCartClick(giftcard, selectedValue)
                }
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}

@Composable
fun DenominationItem(denomination: Denomination) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = denomination.price.toString(),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun DenominationItemPreview() {
    DenominationItem(denomination = Denomination(
        price = 20.0,
        stock = "IN_STOCK",
        currency = "AUD"
    ))
}
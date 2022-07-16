@file:OptIn(ExperimentalMaterialApi::class)

package com.example.giftcardshop.view.screens.detail

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.toCartItem
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
        topBar = {
            DetailTopAppBar(navigateToList = navigateToList)
        },
    ) {
        GiftcardDetailComposable(
            giftcard = selectedGiftcard!!,
            onBuyNowClick = { giftcard, denomination ->
                checkoutViewModel.requestBuyNow(giftcard, denomination)
                navigateToCheckout()
            },
            onAddToCartClick = { giftcard, denomination ->
                giftcardViewModel.addToCart(giftcard, denomination)
            },
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun GiftcardDetailComposable(
    giftcard: Giftcard,
    onBuyNowClick: (Giftcard, Denomination) -> Unit,
    onAddToCartClick: (Giftcard, Denomination) -> Unit,
) {
    val scrollState = rememberScrollState()

    var expended by remember {
        mutableStateOf(false)
    }
    var expandedText by remember {
        mutableStateOf(false)
    }
    var selectedDenomination by remember {
        mutableStateOf(giftcard.denomination.first())
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                enabled = true,
                state = scrollState
            ),
    ) {
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
                text = Html
                    .fromHtml(giftcard.terms, Html.FROM_HTML_MODE_LEGACY)
                    .toString(),
                modifier = Modifier
                    .animateContentSize(tween(500))
                    .clickable { expandedText = !expandedText }
                    .padding(5.dp),
                maxLines = 5,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                fontSize = 13.sp,
            )

            Divider(modifier = Modifier.padding(5.dp))

            Box(
                modifier = Modifier
                    .border(
                        2.dp,
                        MaterialTheme.colors.primary,
                    )
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Value: $ ${selectedDenomination.price}",
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )
                    Box(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "(Pay: $ ${selectedDenomination.payable})",
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Button(
                onClick = { expended = true },
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 170.dp, height = 50.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Select Value",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                DropdownMenu(
                    expanded = expended,
                    onDismissRequest = { expended = false },
                    modifier = Modifier.size(150.dp)
                ) {
                    giftcard.denomination.forEach {
                        DropdownMenuItem(
                            onClick = {
                                expended = false
                                selectedDenomination = it
                            },
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Column(
                                modifier = Modifier.wrapContentSize()
                            ) {
                                DenominationItem(
                                    denomination = it
                                )
                                Divider()
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    onBuyNowClick(giftcard, selectedDenomination)
                },
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 170.dp, height = 50.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Buy Now",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {
                    onAddToCartClick(giftcard, selectedDenomination)
                },
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 170.dp, height = 50.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Add to Cart",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DenominationItem(
    denomination: Denomination
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = "$ ${denomination.price.toInt()}",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(5.dp),
            fontSize = 13.sp
        )
        Box(Modifier.padding(3.dp))
        Text(
            text = "Pay $ ${denomination.payable}",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(5.dp),
            fontSize = 11.sp
        )
    }
}

@Preview
@Composable
fun DenominationItemPreview() {
    DenominationItem(
        denomination = Denomination(
            price = 20.0,
            stock = "IN_STOCK",
            currency = "AUD",
            payable = 199.0
        )
    )
}
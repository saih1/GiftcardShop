@file:OptIn(ExperimentalMaterialApi::class)

package com.example.giftcardshop.view.screens.detail

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.roundToTwoDecimal
import com.example.giftcardshop.view.screens.AsyncImageBox
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
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
    val scaffoldState = rememberScaffoldState()
    val selectedGiftcard: Giftcard? by giftcardViewModel.selectedGiftcard.collectAsState()

    var selectedDenomination by remember {
        mutableStateOf(selectedGiftcard!!.denomination.first())
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { DetailTopAppBar(navigateToList = navigateToList) },
        floatingActionButton = {
            AddToCartFab {
                giftcardViewModel.addToCart(
                    giftcard = selectedGiftcard!!,
                    denomination = selectedDenomination
                )
            }
        }
    ) {
        GiftcardDetailComposable(
            giftcard = selectedGiftcard!!,
            onBuyNowClick = { giftcard, denomination ->
                checkoutViewModel.requestBuyNow(giftcard, denomination)
                navigateToCheckout()
            },
            selectedValue = selectedDenomination,
            onValueSelect = { selectedDenomination = it }
        )
    }
}

@Composable
fun AddToCartFab(onAddToCartClick: () -> Unit) {
    FloatingActionButton(
        onClick = onAddToCartClick,
        modifier = Modifier.alpha(10f)
    ) {
        Icon(imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Add to cart Icon")
    }
}

@ExperimentalMaterialApi
@Composable
fun GiftcardDetailComposable(
    giftcard: Giftcard,
    onBuyNowClick: (Giftcard, Denomination) -> Unit,
    selectedValue: Denomination,
    onValueSelect: (Denomination) -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(enabled = true, state = scrollState)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box {
               AsyncImageBox(imageUrl = giftcard.image,
                   imageWidth = 400.dp, imageHeight = 220.dp)
            }
            Text(
                text = giftcard.brand,
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                modifier = Modifier
                    .padding(5.dp)
                    .align(CenterHorizontally)
            )
            TermsTextBox(giftcard = giftcard)
            Divider()
            ValueSelector(
                denominations = giftcard.denomination,
                selectedValue = selectedValue ,
                onValueSelect = { onValueSelect(it) })
            Divider()
            Button(onClick = { onBuyNowClick(giftcard, selectedValue) },
                enabled = true, shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(15.dp)
                    .size(width = 170.dp, height = 50.dp)
                    .padding(5.dp)
                    .align(CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Buy Now Icon")
                Text(text = "Buy Now",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TermsTextBox(giftcard: Giftcard) {
    var expandedText by remember { mutableStateOf(false) }

    val text: String = when {
        giftcard.terms.isBlank() -> "--"
        else -> Html.fromHtml(giftcard.terms,
            Html.FROM_HTML_MODE_LEGACY).toString()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(
            text = "Terms",
            textAlign = TextAlign.Start,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp))
        ExpandableText(
            expanded = expandedText,
            text = text,
            modifier = Modifier
                .animateContentSize(tween(500))
                .clickable { expandedText = !expandedText }
                .padding(5.dp),
            maxLines = 5,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun ValueSelector(
    denominations: List<Denomination>,
    selectedValue: Denomination,
    onValueSelect: (Denomination) -> Unit
) {
    var expended by remember { mutableStateOf(false) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .padding(10.dp)
    ) {
        TextButton(onClick = { expended = true },
            Modifier
                .alpha(10f)
                .weight(0.4f)
                .shadow(1.dp, shape = RoundedCornerShape(50))
                .background(MaterialTheme.colors.primary)
                .wrapContentSize()
        ) {
            Text(text = "Select Value",
                color = MaterialTheme.colors.onPrimary)
            DropdownMenu(expanded = expended,
                onDismissRequest = { expended = false },
                modifier = Modifier
                    .height(160.dp)
                    .shadow(1.dp, RoundedCornerShape(5.dp))
            ) {
                denominations.forEach { 
                    DropdownMenuItem(onClick = { expended = false
                        onValueSelect(it) },
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Column { DenominationItem(denomination = it) }
                    }
                }
            }
        }
        Card(modifier = Modifier
            .background(MaterialTheme.colors.background)
            .width(200.dp)
            .weight(0.4f)
            .align(CenterVertically)
        ) {
            Row(Modifier.padding(5.dp)) {
                Text(
                    text = "$ ${selectedValue.price.toInt()}",
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.4f))
                Text(
                    text = "Pay $ ${selectedValue.payable.roundToTwoDecimal()}",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(0.6f),
                    fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
fun DenominationItem(denomination: Denomination) {
    Card(modifier = Modifier
        .shadow(10.dp)
        .background(MaterialTheme.colors.background)
        .width(200.dp)
    ) {
        Row(Modifier.padding(5.dp)) {
            Text(
                text = "$ ${denomination.price.toInt()}",
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.4f))
            Text(
                text = "Pay $ ${denomination.payable.roundToTwoDecimal()}",
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(0.6f),
                fontStyle = FontStyle.Italic)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TermBoxPreview() {
    GiftcardShopTheme {
        TermsTextBox(giftcard =
            Giftcard(
                image = "",
                brand = "",
                discount = 0.0,
                terms = "Hello, World, this is an example term",
                denomination = listOf(),
                vendor = "")
        )
    }
}

@Preview
@Composable
fun DenominationItemPreview() {
    GiftcardShopTheme {
        DenominationItem(
            denomination = Denomination(
                price = 20.0,
                stock = "IN_STOCK",
                currency = "AUD",
                payable = 199.0
            )
        )
    }
}

@Preview
@Composable
fun ValueSelectorPreview() {
    GiftcardShopTheme {
        ValueSelector(
            denominations = listOf(
                Denomination(price = 20.0, stock = "IN_STOCK", currency = "AUD", payable = 199.0),
                Denomination(price = 20.0, stock = "IN_STOCK", currency = "AUD", payable = 199.0)),
            selectedValue = Denomination(price = 20.0, stock = "IN_STOCK", currency = "AUD", payable = 199.0),
            onValueSelect = {}
        )
    }
}
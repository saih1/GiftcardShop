package com.example.giftcardshop.view.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.Status.ERROR
import com.example.giftcardshop.shared.Status.SUCCESS
import com.example.giftcardshop.view.screens.AsyncImageBox
import com.example.giftcardshop.view.screens.GenericErrorComposable
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import com.example.giftcardshop.view.viewmodels.CartViewModel
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    checkoutViewModel: CheckoutViewModel,
    navigateToCheckout: () -> Unit,
    navigateToList: () -> Unit
) {
    val cartItems: RequestState<List<CartItem>> by cartViewModel.cartItems.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CartTopAppBar(onClearCartClick = {
                if (cartItems.data?.isNotEmpty() == true) {
                    cartViewModel.clearCart() }
                },
                navigateToList = navigateToList)
        },
        bottomBar = {
            CartBotBar(cartItems = cartItems.data?: emptyList(),
                onCheckoutClick = {
                    checkoutViewModel.requestCheckout(it)
                    navigateToCheckout()
                }
            )
        }
    ) {
        when (cartItems.status) {
            SUCCESS -> {
                when {
                    cartItems.data?.isEmpty() == true -> { EmptyCartScreen() }
                    else -> { CartListComposable(
                        cartItems = cartItems.data!!,
                        onDeleteItemClick = { cartViewModel.deleteFromCart(it) })
                    }
                }
            }
            ERROR -> GenericErrorComposable()
            else -> {}
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartListComposable(
    cartItems: List<CartItem>,
    onDeleteItemClick: (CartItem) -> Unit,
) {
    Column {
        LazyColumn {
            items(items = cartItems, key = { it.id })
            { cartItem ->
                CartItemComposable(cartItem = cartItem,
                    onDeleteItemClick = onDeleteItemClick
                )
            }
        }
    }
}

@Composable
fun CartItemComposable(
    cartItem: CartItem,
    onDeleteItemClick: (CartItem) -> Unit = {}
) {
    Card(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxWidth()
        .padding(5.dp),
        elevation = 5.dp
    ) {
        Row(Modifier.padding(5.dp)) {
            Box(modifier = Modifier.wrapContentSize()
                .padding(5.dp)
                .weight(5f)
            ) {
                AsyncImageBox(
                    imageUrl = cartItem.image,
                    imageWidth = 150.dp,
                    imageHeight = 90.dp)
            }
            Column(Modifier
                .align(CenterVertically)
                .weight(2f)
            ) {
                Text(text = cartItem.brand,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, )
                Text(text = "${cartItem.value}",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, )
                Text(text = "$ ${cartItem.payable}",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, )
            }
            Box(modifier = Modifier
                .padding(5.dp)
                .weight(2f)
            ) {
                IconButton(onClick = { onDeleteItemClick(cartItem) },
                    modifier = Modifier.align(BottomEnd)
                ) {
                    Icon(imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Icon",
                        tint = MaterialTheme.colors.error,
                        modifier = Modifier.shadow(10.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartItemComposableTest(
    cartItem: CartItem,
    onDeleteItemClick: (CartItem) -> Unit = {}
) {
    Surface(
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = { onDeleteItemClick(cartItem) }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 5.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxWidth()
            ) {
                Row {
                   AsyncImageBox(
                       imageUrl = cartItem.image,
                       imageWidth = 150.dp,
                       imageHeight = 90.dp)
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = cartItem.brand,
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                        )
                        Divider(
                            Modifier.width(160.dp)
                        )
                        Text(
                            text = "$ ${cartItem.value}",
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Row() {
                        Box(modifier = Modifier.padding(1.dp))
                        IconButton(onClick = { onDeleteItemClick(cartItem) }) {
                           Icon(
                               imageVector = Icons.Filled.Delete,
                               contentDescription = "Delete Icon",
                               tint = MaterialTheme.colors.primaryVariant
                           )
                       }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CartItemPreviewLight() {
    GiftcardShopTheme(darkTheme = false) {
        CartItemComposable(CartItem(
            id = 0,
            brand = "Nike",
            value = 120.0,
            image = "",
            vendor = "",
            payable = 100.0
        )) {}
    }
}

@Preview
@Composable
fun CartItemPreviewDark() {
    GiftcardShopTheme(darkTheme = true) {
        CartItemComposable(CartItem(
            id = 0,
            brand = "Nike",
            value = 120.0,
            image = "",
            vendor = "",
            payable = 100.0
        )) {}
    }
}
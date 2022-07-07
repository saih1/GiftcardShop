package com.example.giftcardshop.view.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.calculateTotal

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onDeleteItem: (CartItem) -> Unit,
    onCheckout: (amount: Double) -> Unit,
    onClearCart: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column {
            Text(text = "Total: ${cartItems.calculateTotal()}")

            LazyColumn {
                items(
                    items = cartItems,
                    key = { it.id }
                ) { cartItem ->
                    CartItem(
                        cartItem = cartItem,
                        onDeleteItem = onDeleteItem
                    )
                }
            }

            Button(onClick = { onCheckout(cartItems.calculateTotal()) }) {
                Text(text = "Checkout")
            }
            Button(onClick = onClearCart ) {
                Text(text = "Clear Cart")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartItem(
    cartItem: CartItem,
    onDeleteItem: (CartItem) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onDeleteItem(cartItem)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 5.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = cartItem.brand,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = cartItem.value.toString() + " $",
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
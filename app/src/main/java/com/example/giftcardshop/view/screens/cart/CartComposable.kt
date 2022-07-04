package com.example.giftcardshop.view.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.CartItem

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onDeleteItem: (CartItem) -> Unit,
    navigate: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column {
            Text(text = "TOTAL = ${cartItems.sumOf {
                it.value
            }}")

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
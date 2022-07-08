package com.example.giftcardshop.view.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.view.screens.AsyncImageBox

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GiftcardListScreen(
    giftcards: List<Giftcard>,
    onItemClick: (Giftcard) -> Unit,
    onLogoutClick: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToDetail: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Column() {
                LazyColumn {
                    items(
                        items = giftcards,
                        key = { it.brand }
                    ) { giftcard ->
                        GiftcardItem(
                            giftcard = giftcard,
                            onItemClick = onItemClick,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }
            }
            Button(onClick = onLogoutClick) {
                Text(text = "Sign out")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToCart) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
            }
        }
    )
}

// TEST
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GiftcardItem(
    giftcard: Giftcard,
    onItemClick: (Giftcard) -> Unit,
    navigateToDetail: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onItemClick(giftcard)
            navigateToDetail.invoke()
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
                    text = giftcard.brand,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = giftcard.vendor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            AsyncImageBox(imageUrl = giftcard.image)
        }
    }
}
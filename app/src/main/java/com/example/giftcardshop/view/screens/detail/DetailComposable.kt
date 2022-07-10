package com.example.giftcardshop.view.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.data.network.dto.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.view.screens.AsyncImageBox

@Composable
fun GiftcardDetailScreen(
    giftcard: Giftcard,
    onBuyNowClick: (Giftcard, Double) -> Unit,
    onAddToCartClick: (Giftcard, Double) -> Unit,
    navigate: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        var expended: Boolean by remember {
            mutableStateOf(false)
        }

        var selectedValue: Double by remember {
            mutableStateOf(giftcard.denominations.first().price)
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImageBox(
                imageUrl = giftcard.image,
                imageWidth = 400.dp,
                imageHeight = 220.dp
            )
            Divider()

            Text(text = "Selected Value : $selectedValue")

            Text(
                text = giftcard.brand,
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            )
            Button(onClick = { expended = true }) {
                Text(text = "Value")
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
                    navigate.invoke()
                }
            ) {
                Text(text = "Buy Now")
            }
            Button(
                onClick = {
                    onAddToCartClick(giftcard, selectedValue)
                    navigate.invoke()
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
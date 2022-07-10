package com.example.giftcardshop.view.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.calculateTotal
import com.example.giftcardshop.shared.roundToTwoDecimal

@Composable
fun CartBotBar(
    cartItems: List<CartItem>,
    onCheckoutClick: (amount: Double) -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .align(alignment = Alignment.CenterVertically)
                .width(220.dp)
        ) {
            Text(
                text = "TOTAL: $ ${cartItems
                    .calculateTotal()
                    .roundToTwoDecimal()}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Button(
            onClick = { onCheckoutClick(cartItems.calculateTotal()) },
            enabled = cartItems.isNotEmpty(),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 170.dp, height = 50.dp)
                .padding(5.dp)
        ) {
            Text(
                text = "Checkout",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
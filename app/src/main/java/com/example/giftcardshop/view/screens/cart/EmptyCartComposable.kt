package com.example.giftcardshop.view.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.R
import com.example.giftcardshop.view.ui.theme.LightGray
import com.example.giftcardshop.view.ui.theme.MediumGray

@Preview
@Composable
fun EmptyCartScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_baseline_cancel),
            contentDescription = "Empty Cart",
            tint = MediumGray
        )
        Text(
            text = "Empty Cart",
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}
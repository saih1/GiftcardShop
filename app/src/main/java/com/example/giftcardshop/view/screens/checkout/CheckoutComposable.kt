package com.example.giftcardshop.view.screens.checkout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.R
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.GenericErrorComposable
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.viewmodels.CheckoutViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CheckoutScreen(
    checkoutViewModel: CheckoutViewModel,
    navigateToList: () -> Unit
) {
    val checkoutStatus by checkoutViewModel.checkoutStatus.collectAsState()

    Scaffold {
        when (checkoutStatus.status) {
            Status.LOADING -> ProgressBar()
            Status.SUCCESS -> {
                if (checkoutStatus.data == false) {
                    FailCheckoutComposable(
                        onContinueClick = navigateToList
                    )
                } else {
                    CheckoutComposable(
                        onContinueClick = navigateToList
                    )
                }
            }
            Status.ERROR -> GenericErrorComposable()
            else -> { }
        }
    }
}

@Preview
@Composable
fun CheckoutComposable(
    onContinueClick: () -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_baseline_done_all_24),
                contentDescription = "Successful Checkout",
                tint = Color.Green
            )
            Text(
                text = "You have successfully checked out",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.padding(15.dp)
            )
            Button(
                onClick = onContinueClick,
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(10.dp)
            ) {
                Text(
                    text = "Continue Shopping",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun FailCheckoutComposable(
    onContinueClick: () -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_baseline_cancel),
                contentDescription = "Unsuccessful Checkout",
                tint = Color.Red
            )
            Text(
                text = "Unsuccessful Checkout",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.padding(15.dp)
            )
            Button(
                onClick = onContinueClick,
                enabled = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(10.dp)
            ) {
                Text(
                    text = "Continue",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
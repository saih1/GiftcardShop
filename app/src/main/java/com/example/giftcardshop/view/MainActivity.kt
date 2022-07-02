package com.example.giftcardshop.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.cart_list.CartViewModel
import com.example.giftcardshop.view.giftcard_list.GiftcardListViewModel
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val giftcardListViewModel: GiftcardListViewModel by viewModels()
        val cartViewModel: CartViewModel by viewModels()
        setContent {
            cartViewModel.addCart()
            val giftcards = giftcardListViewModel.giftcards.collectAsState()
            GiftcardShopTheme {
                if (giftcards.value.status == Status.ERROR) {
                    ErrorScreen()
                } else if (giftcards.value.status == Status.SUCCESS) {
                    GiftcardListScreen(
                        giftcards = giftcards.value.data ?: emptyList(),
                        giftcardListViewModel = giftcardListViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Surface(
        color = Color.Blue,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "ERROR",
            fontSize = 50.sp
        )
    }
}

@Composable
fun GiftcardListScreen(
    giftcards: List<Giftcard>,
    giftcardListViewModel: GiftcardListViewModel
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        LazyColumn {
            items(
                items = giftcards,
                key = { it.brand }
            ) { giftcard ->
                GiftcardItem(giftcard = giftcard)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GiftcardItem(
    giftcard: Giftcard
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {  }
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
        }
    }
}
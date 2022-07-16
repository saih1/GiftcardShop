package com.example.giftcardshop.view.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.Status.*
import com.example.giftcardshop.shared.toPercentage
import com.example.giftcardshop.view.screens.AsyncImageBox
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GiftcardListScreen(
    giftcardViewModel: GiftcardViewModel,
    loginViewModel: LoginViewModel,
    navigateToCart: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToDetail: () -> Unit
) {
    val giftcards by giftcardViewModel.giftcards.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainTopAppBar(
                loginViewModel = loginViewModel,
                navigateToCart = navigateToCart,
                navigateToLogin = navigateToLogin
            )
        },
    ) {
        when (giftcards.status) {
            SUCCESS -> {
                GiftcardList(
                    giftcards = giftcards.data!!,
                    onItemClick = { giftcard ->
                        giftcardViewModel.selectGiftcard(giftcard)
                        navigateToDetail()
                    },
                )
            }
            LOADING -> ProgressBar()
//            ERROR -> // TODO Error Composable
        }
    }
}

@Composable
fun GiftcardList(
    giftcards: List<Giftcard>,
    onItemClick: (Giftcard) -> Unit,
) {
    Column {
        LazyColumn {
            items(
                items = giftcards,
                key = { it.brand }
            ) { giftcard ->
                GiftcardItem(
                    giftcard = giftcard,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GiftcardItem(
    giftcard: Giftcard,
    onItemClick: (Giftcard) -> Unit
) {
    Surface(
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onItemClick(giftcard) }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
        ) {
            Row {
                AsyncImageBox(
                    imageUrl = giftcard.image,
                    imageWidth = 150.dp,
                    imageHeight = 90.dp
                )
                Column(
                    Modifier.padding(10.dp)
                ) {
                    Text(
                        text = giftcard.brand,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Text(
                        text = giftcard.vendor,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box(modifier = Modifier.size(5.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .size(80.dp, 20.dp)
                            .background(
                                MaterialTheme.colors.primary, shape = CircleShape
                            )
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = "Save ${(giftcard.discount).toPercentage()}%",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.sp
                                ),
                                fontWeight = FontWeight.Light,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GiftcardItemPreview() {
    GiftcardItem(
        giftcard = Giftcard(
            image = "link",
            brand = "Apple Store Gift Card",
            discount = 60.74,
            terms = "",
            denomination = listOf(),
            vendor = "DigitalGlue"
        ),
        onItemClick = {}
    )
}
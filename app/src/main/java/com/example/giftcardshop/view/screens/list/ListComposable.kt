@file:OptIn(ExperimentalMaterialApi::class)

package com.example.giftcardshop.view.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.Status.LOADING
import com.example.giftcardshop.shared.Status.SUCCESS
import com.example.giftcardshop.shared.toPercentage
import com.example.giftcardshop.view.screens.AsyncImageBox
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import com.example.giftcardshop.view.viewmodels.GiftcardViewModel
import com.example.giftcardshop.view.viewmodels.LoginViewModel

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
    ) { paddingValues ->
        when (giftcards.status) {
            SUCCESS -> {
                GiftcardList(
                    giftcards = giftcards.data!!,
                    onItemClick = { giftcard ->
                        giftcardViewModel.selectGiftcard(giftcard)
                        navigateToDetail()
                    },
                    padding = paddingValues
                )
            }
            LOADING -> ProgressBar()
//            ERROR -> // TODO Error Composable
            else -> {}
        }
    }
}

@Composable
fun GiftcardList(
    giftcards: List<Giftcard>,
    onItemClick: (Giftcard) -> Unit,
    padding: PaddingValues
) {
    Column(Modifier.padding(padding)) {
        LazyColumn {
            items(items = giftcards, key = { it.brand })
            { giftcard -> GiftcardItem(giftcard = giftcard,
                onItemClick = onItemClick, )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GiftcardItem(giftcard: Giftcard, onItemClick: (Giftcard) -> Unit) {
    Card(
        onClick = { onItemClick(giftcard) },
        modifier = Modifier.background(MaterialTheme.colors.background)
            .padding(5.dp),
        elevation = 5.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .alpha(5f)
                .align(CenterVertically)
                .padding(5.dp)) {
                AsyncImageBox(
                    imageUrl = giftcard.image,
                    imageWidth = 150.dp, imageHeight = 90.dp)
            }
            Column(modifier = Modifier.alpha(5f)
                .align(CenterVertically).padding(5.dp)
            ) {
                Text(text = giftcard.brand,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(3.dp).alpha(1f))
                Text(text = giftcard.vendor,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(3.dp).alpha(1f))
                Box(modifier = Modifier
                    .padding(3.dp)
                    .alpha(1f)
                    .size(80.dp, 20.dp)
                    .background(color = MaterialTheme.colors.primary,
                        shape = CircleShape)
                ) {
                    Text(text = "Save ${(giftcard.discount).toPercentage()}%",
                        style = TextStyle(color = MaterialTheme.colors.onPrimary,
                            fontSize = 12.sp),
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NewGiftcardItemPreviewDark() {
    GiftcardShopTheme(darkTheme = true) {
        GiftcardItem(
            giftcard = Giftcard(
                image = "",
                brand = "Apple Store Gift Card",
                discount = 60.74,
                terms = "",
                denomination = listOf(),
                vendor = "DigitalGlue"
            ),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
fun NewGiftcardItemPreviewLight() {
    GiftcardShopTheme(darkTheme = false) {
        GiftcardItem(
            giftcard = Giftcard(
                image = "",
                brand = "Apple Store Gift Card",
                discount = 60.74,
                terms = "",
                denomination = listOf(),
                vendor = "DigitalGlue"
            ),
            onItemClick = {}
        )
    }
}
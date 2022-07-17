package com.example.giftcardshop.view.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.view.ui.theme.Shapes
import com.example.giftcardshop.view.viewmodels.LoginViewModel

@Composable
fun MainTopAppBar(
    loginViewModel: LoginViewModel,
    navigateToCart: () -> Unit,
    navigateToLogin: () -> Unit
) {
    StatelessTopAppBar(
        onLogoutClick = {
            loginViewModel.signOut()
            navigateToLogin()
        },
        navigateToCart = navigateToCart
    )
}

@Composable
fun StatelessTopAppBar(
    onLogoutClick: () -> Unit,
    navigateToCart: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Giftcard",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Shop",
                textAlign = TextAlign.End,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .shadow(elevation = 20.dp),
        actions = {
            MainTopAppBarActions(
                onLogoutClick = onLogoutClick,
                navigateToCart = navigateToCart
            )
        }
    )
}

@Composable
fun MainTopAppBarActions(
    onLogoutClick: () -> Unit,
    navigateToCart: () -> Unit
) {
    Row {
        AppBarCartAction(navigateToCart = navigateToCart)
        AppBarProfileAction(onLogoutClick =  onLogoutClick)
    }
}

@Composable
fun AppBarCartAction(
    navigateToCart: () -> Unit
) {
    IconButton(
        onClick = navigateToCart
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Shopping Cart Icon",
        )
    }
}

@Composable
fun AppBarProfileAction(
    onLogoutClick: () -> Unit
) {
    var expended by remember { mutableStateOf(false) }

    IconButton(onClick = { expended = true }) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile Icon",
        )
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false }) {
            DropdownMenuItem(
                onClick = {
                    expended = false
                    onLogoutClick()
                }
            ) {
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(5.dp),
                    fontFamily = FontFamily.Monospace
                )
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Shopping Cart Icon",
                )
            }
        }
    }
}

@Preview
@Composable
fun MainTopAppBarPreview() {
    Surface(
        Modifier
            .wrapContentSize()
            .padding(10.dp)
            .background(MaterialTheme.colors.background)
    ) {
        StatelessTopAppBar(
            onLogoutClick = {},
            navigateToCart = {}
        )
    }
}
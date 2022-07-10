package com.example.giftcardshop.view.screens.cart

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun CartTopAppBar(
    onClearCartClick: () -> Unit,
    navigateToList: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = navigateToList
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Navigation Back Arrow"
                )
            }
        },
        title = {
            Text(text = "Cart")
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.shadow(elevation = 10.dp),
        actions = {
            ClearCartAction(
                onClearCartClick = onClearCartClick
            )
        }
    )
}

@Composable
fun ClearCartAction(
    onClearCartClick: () -> Unit
) {
    var expended by remember { mutableStateOf(false) }

    IconButton(onClick = { expended = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "More Vert Icon",
        )
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false }) {
            DropdownMenuItem(
                onClick = {
                    expended = false
                    onClearCartClick()
                }
            ) {
                Text(
                    text = "Clear",
                    modifier = Modifier.padding(5.dp),
                    fontFamily = FontFamily.Monospace
                )
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear Icon",
                )
            }
        }
    }
}
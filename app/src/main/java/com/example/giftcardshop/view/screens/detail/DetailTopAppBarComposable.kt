package com.example.giftcardshop.view.screens.detail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun DetailTopAppBar(
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
        title = {},
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.shadow(elevation = 10.dp),
    )
}
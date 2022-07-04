package com.example.giftcardshop.view.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.domain.model.Giftcard

@Composable
fun GiftcardListScreen(
    giftcards: List<Giftcard>,
    onItemSelect: (Giftcard) -> Unit,
    navigate: () -> Unit
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
                GiftcardItem(
                    giftcard = giftcard,
                    onItemSelect = onItemSelect,
                    navigate = navigate
                )
            }
        }
    }
}

// TEST
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GiftcardItem(
    giftcard: Giftcard,
    onItemSelect: (Giftcard) -> Unit,
    navigate: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onItemSelect(giftcard)
            navigate.invoke()
        }
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
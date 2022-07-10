package com.example.giftcardshop.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.giftcardshop.R

@Composable
fun GenericErrorComposable() {
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
                contentDescription = "ERROR",
                tint = Color.Red
            )
        }
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun AsyncImageBox(
    imageUrl: String,
    imageWidth: Dp,
    imageHeight: Dp
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        var placeholder: MemoryCache.Key? = null
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .scale(Scale.FIT)
                .crossfade(true)
                .crossfade(200)
                .build()
            ,
            onSuccess = {
                placeholder = it.result.memoryCacheKey
            },
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(imageWidth, imageHeight)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(5.dp)),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
        )
    }
}

@Preview
@Composable
fun PreviewAsyncImageBox() {
    Surface {
        AsyncImageBox(
            imageUrl = "",
            imageWidth = 150.dp,
            imageHeight = 90.dp
        )
    }
}

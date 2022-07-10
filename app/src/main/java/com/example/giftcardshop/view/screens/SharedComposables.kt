package com.example.giftcardshop.view.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1000
) {

    // circle's scale state
    var circleScale by remember {
        mutableStateOf(0f)
    }

    // animation
    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )

    // This is called when the app is launched
    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    // animating circle
    Box(
        modifier = Modifier
            .size(size = 250.dp)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 4.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    )
}

@Composable
fun LoadingAnimation1(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colors.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val lastCircle = circleValues.size - 1

    Row(modifier = modifier) {
        circleValues.forEachIndexed { index, value ->
            Box(modifier = Modifier
                .size(circleSize)
                .graphicsLayer { translationY = -value * distance }
                .background(color = circleColor, shape = CircleShape)
            )
            if (index != lastCircle) Spacer(modifier = Modifier.width(spaceBetween))
        }
    }
}

@Composable
fun TestGenericComposable(
    text: String = "Hello",
    buttonText: String = "Button Text",
    navigate: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            )
            Button(
                onClick = navigate
            ) {
                Text(text = buttonText)
            }
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
            modifier = Modifier.align(Alignment.Center),
            color = Color.Blue,
            strokeWidth = 10.dp
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
        modifier = Modifier.background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        var placeholder: MemoryCache.Key? = null
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl).crossfade(true)
                .size(Size.ORIGINAL).scale(Scale.FILL)
                .crossfade(350)
                .build()
            ,
            onSuccess = {
                placeholder = it.result.memoryCacheKey
            },
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(imageWidth, imageHeight)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.background)
        )
    }
}

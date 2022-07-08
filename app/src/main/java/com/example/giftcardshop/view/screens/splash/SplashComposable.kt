package com.example.giftcardshop.view.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.giftcardshop.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToList: () -> Unit
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val offSetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 500
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500L)
        navigateToList.invoke()
    }

    Splash(offSetState = offSetState, alphaState = alphaState)
}

@Composable
fun Splash(
    offSetState: Dp,
    alphaState: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .offset(y = offSetState)
                .alpha(alpha = alphaState),
            painter = painterResource(
                id = R.drawable.ic_launcher_background),
            contentDescription = "Splash"
        )
    }
}
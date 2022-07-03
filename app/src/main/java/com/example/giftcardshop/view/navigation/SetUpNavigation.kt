@file:OptIn(ExperimentalAnimationApi::class)

package com.example.giftcardshop.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.Constants.CART_SCREEN
import com.example.giftcardshop.shared.Constants.DETAIL_SCREEN
import com.example.giftcardshop.shared.Constants.LIST_SCREEN
import com.example.giftcardshop.shared.Constants.LOGIN_SCREEN
import com.example.giftcardshop.shared.Constants.SPLASH_SCREEN
import com.example.giftcardshop.view.cart_view.CartViewModel
import com.example.giftcardshop.view.giftcard_view.GiftcardViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay

@Composable
fun SetUpNavigation(
    navController: NavHostController,
    giftcardViewModel: GiftcardViewModel,
    cartViewModel: CartViewModel
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {

        composable(
            route = SPLASH_SCREEN,
        ) {
            // SPLASH COMPOSABLE
            TestGenericComposable(
                text = "Splash!"
            ) {
                navController.navigate(LOGIN_SCREEN) {
                    popUpTo(SPLASH_SCREEN) {
                        inclusive = true
                    }
                }
            }
        }

        composable(
            route = LOGIN_SCREEN,
        ) {
            // LOGIN COMPOSABLE
            TestGenericComposable(
                text = "LOGIN!"
            ) {
                navController.navigate(LIST_SCREEN) {

                }
            }
        }

        composable(
            route = LIST_SCREEN,
        ) {
            // LIST_SCREEN
            TestGenericComposable(
                text = "LIST!"
            ) {
                navController.navigate(DETAIL_SCREEN)
            }
        }

        composable(
            route = DETAIL_SCREEN,
        ) {
            // DETAIL COMPOSABLE
            TestGenericComposable(
                text = "DETAIL!"
            ) {
                navController.navigate(CART_SCREEN)
            }
        }

        composable(
            route = CART_SCREEN,
        ) {
            // CART COMPOSABLE
            TestGenericComposable(
                text = "CART!"
            ) {
                navController.navigate(LIST_SCREEN)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestGenericComposable(
    text: String = "Hello",
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
                Text(text = "Navigate")
            }
        }
    }
}

@Composable
fun GiftcardListScreen(
    giftcards: List<Giftcard>,
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

// TEST
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
        remember { Animatable(initialValue = 0f) },
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
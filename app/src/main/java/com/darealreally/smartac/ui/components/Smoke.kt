package com.darealreally.smartac.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.darealreally.smartac.R
import com.darealreally.smartac.data.TestData
import kotlin.math.roundToInt

@Composable
fun SmokeIllustration(
    modifier: Modifier = Modifier,
    color: Color = TestData.appThemeCold.onPrimary
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_smoke_illustration),
        contentDescription = null,
        tint = color.copy(alpha = 0.4F),
        modifier = modifier.rotate(degrees = -5F) // clockwise
    )
}

@Composable
fun Smoke(
    modifier: Modifier = Modifier
) {
    // State
    val infiniteTransition = rememberInfiniteTransition()

    // Props
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.dp.toPx() }
    val offsetY = with(LocalDensity.current) { 400.dp.toPx() }

    // Transition
    val animationSpec: (duration: Int) -> InfiniteRepeatableSpec<Float> = { duration ->
        infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    }

    val smokeXOffset by infiniteTransition.animateFloat(
        initialValue = -(screenWidthPx * 1.2F),
        targetValue = screenWidthPx * 1.5F,
        animationSpec = animationSpec(30_000)
    )
    val smoke2XOffset by infiniteTransition.animateFloat(
        initialValue = -(screenWidthPx * 0.5F),
        targetValue = screenWidthPx * 1.2F,
        animationSpec = animationSpec(40_000)
    )
    val smoke3XOffset by infiniteTransition.animateFloat(
        initialValue = -(screenWidthPx * 0.5F),
        targetValue = screenWidthPx * 1.2F,
        animationSpec = animationSpec(35_000)
    )

    val smokeScale = 1.5F

    // UI
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {

        SmokeIllustration(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = smoke2XOffset.roundToInt(),
                        y = -offsetY.roundToInt()
                    )
                }
                .scale(smokeScale)
        )

        SmokeIllustration(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = smoke3XOffset.roundToInt(),
                        y = offsetY.roundToInt()
                    )
                }
                .scale(smokeScale)
        )

        SmokeIllustration(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = smokeXOffset.roundToInt(),
                        y = 0
                    )
                }
                .scale(smokeScale + 0.5F)
        )

    }
}




/**
 * Preview Section
 */
@Preview
@Composable
fun SmokeIllustrationPreview() {
    SmokeIllustration()
}

@Preview
@Composable
fun SmokePreview() {
    Smoke()
}
package com.darealreally.smartac.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.darealreally.smartac.data.TestData
import kotlin.math.roundToInt

@Composable
fun SnowBall(
    initialOffsetDp: Offset = TestData.snowBallOffset,
    color: Color = TestData.appThemeCold.onPrimary,
    animationDelayMillis: Int = (5_000..30_000).random()
) {
    // Props
    val initialXOffsetPx = with(LocalDensity.current) { initialOffsetDp.x.dp.toPx() }
    val initialYOffsetPx = with(LocalDensity.current) { initialOffsetDp.y.dp.toPx() }

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenHeightPx = with(LocalDensity.current) { screenHeight.dp.toPx() }
    val lengthDp = remember { 12.dp }
    val lengthPx = with(LocalDensity.current) { lengthDp.toPx() }

    // State
    val infiniteTransition = rememberInfiniteTransition()
    var xOffset by remember { mutableStateOf(initialXOffsetPx) }
    val yOffset by remember { mutableStateOf(initialYOffsetPx) }

    // Transition
    val animatedYOffset by infiniteTransition.animateFloat(
        initialValue = yOffset,
        targetValue = screenHeightPx * 1.4F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 35_000,
                easing = LinearEasing,
                delayMillis = animationDelayMillis
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    // UI
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = xOffset.roundToInt(),
                    y = animatedYOffset.roundToInt()
                )
            }
            .size(lengthDp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.5F),
                        color.copy(alpha = 0.2F)
                    ),
                    center = Offset(lengthPx / 3, lengthPx / 3),
                    radius = lengthPx * 0.7F
                )
            )
            .pointerInput(Unit) {
                detectDragGestures { change, (amountX, _) ->
                    change.consumeAllChanges()
                    xOffset += amountX
                    // yOffset += amountY
                }
            }
    )
}

@Composable
fun SnowBalls(
    modifier: Modifier = Modifier
) {
    Log.d("App", "SnowBalls called")

    // Props
    val ballOffsets = remember {
        listOf(
            Offset(x = 30F, y = -20F),
            Offset(x = 334F, y = -20F),
            Offset(x = 131F, y = -20F),
            Offset(x = 30F, y = -20F),
            Offset(x = 63F, y = -20F),
            Offset(x = 294F, y = -20F),
            Offset(x = 331F, y = -20F),
            Offset(x = 84F, y = -20F),
            Offset(x = 350F, y = -20F),
            Offset(x = 78F, y = -20F),
            Offset(x = 259F, y = -20F),
            Offset(x = 200F, y = -20F),
            Offset(x = 20F, y = -20F),
            Offset(x = 59F, y = -20F),
            Offset(x = 80F, y = -20F)
        )
    }

    // UI
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
            .then(modifier)
    ) {
        ballOffsets.forEachIndexed { index, offset ->
            if (index == 0) SnowBall(offset, animationDelayMillis = 0)
            else SnowBall(offset)

        }
    }
}



/**
 * Preview Section
 */
@Preview(name = "Snowball")
@Composable
fun SnowBallPreview() {
    SnowBall()
}

@Preview(name = "Snowballs")
@Composable
fun SnowBallsPreview() {
    SnowBalls()
}
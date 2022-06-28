package com.darealreally.smartac.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darealreally.smartac.data.TempConstant
import com.darealreally.smartac.data.TempUnit
import com.darealreally.smartac.data.Temperature
import com.darealreally.smartac.data.TestData
import com.darealreally.smartac.ui.AppTheme
import com.darealreally.smartac.ui.theme.SmartACTheme
import com.darealreally.smartac.utils.celsiusToKelvin
import kotlin.math.roundToInt

@Composable
fun TempDisplay(
    temperature: Temperature = TestData.temp,
    setTempUnit: (TempUnit) -> Unit = {},
    isTurnedOn: Boolean = true,
    appTheme: AppTheme = TestData.appThemeInactive
) {
    // Log.d("App", "TempDisplay called")

    // Props
    val (_, unit) = temperature

    // UI
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .alpha(if (isTurnedOn) 1F else 0.5F),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Col 1: TEMPERATURE IN CONVERTED VALUE (F/C)
        Text(
            text = "${temperature.convertedValue.roundToInt()}",
            style = MaterialTheme.typography.h2,
            color = appTheme.onPrimary
        )

        // Col 2: UNIT TOGGLE
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {

            // Row 1: Celsius
            UnitToggleButton(
                unit = TempUnit.Celsius,
                enabled = isTurnedOn,
                isSelected = unit == TempUnit.Celsius,
                color = appTheme.onPrimary,
                onClick = { setTempUnit(TempUnit.Celsius) }
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Row 2: Fahrenheit
            UnitToggleButton(
                unit = TempUnit.Fahrenheit,
                enabled = isTurnedOn,
                isSelected = unit == TempUnit.Fahrenheit,
                color = appTheme.onPrimary,
                onClick = { setTempUnit(TempUnit.Fahrenheit) }
            )
        } //: Column
    } //: Row
}


@Composable
fun UnitToggleButton(
    unit: TempUnit,
    enabled: Boolean,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Text(
        text = unit.unitSymbol,
        style = MaterialTheme.typography.h5,
        color = color.copy(
            alpha = if (isSelected) 1F else 0.3F
        ),
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
    )
}


@Composable
fun TempSlider(
    setKelvinValue: (Double) -> Unit = {},
    isTurnedOn: Boolean = true,
    appTheme: AppTheme = TestData.appThemeInactive
) {
    // Log.d("App", "TempSlider called")

    // State
    var offsetX by remember { mutableStateOf(100F) }

    // Props
    val maxHeightDp = 416.dp
    val maxHeightPx = with(LocalDensity.current) { maxHeightDp.toPx() }

    // Animation
    val color: Color by animateColorAsState(appTheme.onPrimary)

    // Side Effect
    LaunchedEffect(offsetX) {
        val heightRatio = offsetX / maxHeightPx
        val celsiusToAdd = heightRatio.toDouble() * TempConstant.tempDifferentCelsius
        val roundedCelsiusToAdd = celsiusToAdd.roundToInt()
        setKelvinValue(
            (TempConstant.minTempCelsius + roundedCelsiusToAdd).celsiusToKelvin()
        )
    }

    // UI
    Box(
        modifier = Modifier
            .width(157.dp)
            .height(maxHeightDp)
            .clip(RoundedCornerShape(100))
            .background(
                color.copy(alpha = if (isTurnedOn) 0.4F else 0.1F)
            )
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { amount ->
                    val newXOffset = offsetX - amount
                    val beyondMax = newXOffset > maxHeightPx
                    val beyondMin = newXOffset < 0
                    if (beyondMax || beyondMin || !isTurnedOn) {
                        return@rememberDraggableState
                    }
                    offsetX = newXOffset
                }
            )
            .alpha(if (isTurnedOn) 1F else 0.5F),
        contentAlignment = Alignment.BottomCenter
    ) {
        MeniscusFill(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { offsetX.toDp() })
                .scale(1.5F),
            color = color.copy(alpha = 0.7F)
        )
    }
}

@Composable
fun MeniscusFill(
    modifier: Modifier = Modifier,
    color: Color = TestData.appThemeCold.onPrimary
) {
    Canvas(modifier = modifier) {
        val fillPath = Path().apply {
            val (width, height) = size

            val p1 = Offset.Zero
            val p2 = Offset(0F, height)
            val p3 = Offset(width, height)
            val p4 = Offset(width, 0F)
            val p5 = Offset(0.5F * width, 40F)

            moveTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
            lineTo(p4.x, p4.y)
            lineTo(p5.x, p5.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
        }

        this.drawIntoCanvas {
            it.drawOutline(
                outline = Outline.Generic(fillPath),
                Paint().apply {
                    this.color = color
                    style = PaintingStyle.Fill
                    strokeCap = StrokeCap.Round
                    strokeJoin = StrokeJoin.Round
                    pathEffect = PathEffect.cornerPathEffect(100F)
                    isAntiAlias = true
                }
            )
        }
    }
}




/**
 * Preview Section
 */
@Preview
@Composable
fun MeniscusFillPreview() {
    SmartACTheme {
        MeniscusFill(
            modifier = Modifier
                .width(500.dp)
                .height(50.dp)
                .padding(10.dp)
        )
    }
}

@Preview(name = "Inactive")
@Composable
fun TempDisplayPreview() {
    SmartACTheme {
        TempDisplay()
    }
}

@Preview(name = "Cold")
@Composable
fun TempDisplayColdPreview() {
    SmartACTheme {
        TempDisplay(
            appTheme = TestData.appThemeCold
        )
    }
}

@Preview(name = "Hot")
@Composable
fun TempDisplayHotPreview() {
    SmartACTheme {
        TempDisplay(
            appTheme = TestData.appThemeHot
        )
    }
}

@Preview(name = "Inactive")
@Composable
fun TempSliderPreview() {
    SmartACTheme {
        TempSlider()
    }
}

@Preview(name = "Cold")
@Composable
fun TempSliderColdPreview() {
    SmartACTheme {
        TempSlider(
            appTheme = TestData.appThemeCold
        )
    }
}

@Preview(name = "Hot")
@Composable
fun TempSliderHotPreview() {
    SmartACTheme {
        TempSlider(
            appTheme = TestData.appThemeHot
        )
    }
}
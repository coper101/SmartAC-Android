package com.darealreally.smartac.ui.home.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darealreally.smartac.data.TempConstant
import com.darealreally.smartac.data.TempUnit
import com.darealreally.smartac.data.Temperature
import com.darealreally.smartac.data.TestData
import com.darealreally.smartac.ui.theme.SmartACTheme
import com.darealreally.smartac.utils.celsiusToKelvin
import kotlin.math.roundToInt

@Composable
fun TempDisplay(
    temperature: Temperature = TestData.temp,
    setTempUnit: (TempUnit) -> Unit = {},
    isTurnedOn: Boolean = true
) {
    // Props
    val (_, unit) = temperature

    // UI
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Col 1: TEMPERATURE IN CONVERTED VALUE (F/C)
        Text(
            text = "${temperature.convertedValue.roundToInt()}",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onPrimary
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
                onClick = { setTempUnit(TempUnit.Celsius) }
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Row 2: Fahrenheit
            UnitToggleButton(
                unit = TempUnit.Fahrenheit,
                enabled = isTurnedOn,
                isSelected = unit == TempUnit.Fahrenheit,
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
    onClick: () -> Unit
) {
    Text(
        text = unit.unitSymbol,
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onPrimary.copy(
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
    isTurnedOn: Boolean = true
) {
    // Log.d("App", "TempSlider called")
    // State
    var offsetX by remember { mutableStateOf(100F) }

    // Props
    val color = MaterialTheme.colors.onPrimary
    val maxHeightDp = 416.dp
    val maxHeightPx = with(LocalDensity.current) { maxHeightDp.toPx() }

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
                    if (beyondMax || beyondMin) {
                        return@rememberDraggableState
                    }
                    offsetX = newXOffset
                }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { offsetX.toDp() })
                .background(color)
        )
    }
}



/**
 * Preview Section
 */
@Preview
@Composable
fun TempDisplayPreview() {
    SmartACTheme {
        TempDisplay()
    }
}

@Preview
@Composable
fun TempSliderPreview() {
    SmartACTheme {
        TempSlider()
    }
}
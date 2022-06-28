package com.darealreally.smartac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darealreally.smartac.R
import com.darealreally.smartac.data.TestData
import com.darealreally.smartac.ui.AppTheme
import com.darealreally.smartac.ui.Theme
import com.darealreally.smartac.ui.theme.SmartACTheme

@Composable
fun BottomControls(
    isTurnedOn: Boolean = true,
    toggleOn: () -> Unit = {},
    toggleCool: () -> Unit = {},
    toggleFan: () -> Unit = {},
    appTheme: AppTheme = TestData.appThemeInactive
) {
    // UI
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        // Col 1: POWER
        ControlButton(
            iconId = R.drawable.ic_power,
            color = appTheme.onPrimary,
            backgroundColor = appTheme.background,
            enabled = true,
            onClick = toggleOn
        )

        // Col 2: COOL
        ControlButton(
            iconId = R.drawable.ic_cool,
            color = appTheme.onPrimary,
            backgroundColor = appTheme.background,
            enabled = isTurnedOn && appTheme.theme == Theme.Cold,
            onClick = toggleCool
        )

        // Col 3: FAN
        ControlButton(
            iconId = R.drawable.ic_fan,
            color = appTheme.onPrimary,
            backgroundColor = appTheme.background,
            enabled = isTurnedOn,
            onClick = toggleFan
        )
    }
}


@Composable
fun ControlButton(
    iconId: Int = R.drawable.ic_cool,
    color: Color = TestData.appThemeInactive.onPrimary,
    backgroundColor: Color = TestData.appThemeInactive.background,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    // UI
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(
                backgroundColor.copy(
                    alpha = if (enabled) 0.5F else 0.1F
                )
            )
            .border(
                width = 0.5.dp,
                color = color.copy(alpha = 0.1F),
                shape = CircleShape
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .alpha(if (enabled) 1F else 0.5F),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
            modifier = Modifier.fillMaxSize(0.8F)
        )
    }
}




/**
 * Preview Section
 */
@Preview
@Composable
fun BottomControlsPreview() {
    SmartACTheme {
        BottomControls()
    }
}

@Preview(name = "Cold")
@Composable
fun ControlButtonColdPreview() {
    SmartACTheme {
        ControlButton(
            color = TestData.appThemeCold.onPrimary,
            backgroundColor = TestData.appThemeCold.background
        )
    }
}

@Preview(name = "Hot")
@Composable
fun ControlButtonHotPreview() {
    SmartACTheme {
        ControlButton(
            color = TestData.appThemeHot.onPrimary,
            backgroundColor = TestData.appThemeHot.background
        )
    }
}

@Preview(name = "Inactive")
@Composable
fun ControlButtonPreview() {
    SmartACTheme {
        ControlButton(
            enabled = false
        )
    }
}
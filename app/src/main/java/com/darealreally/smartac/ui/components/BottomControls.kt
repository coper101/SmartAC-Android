package com.darealreally.smartac.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.darealreally.smartac.ui.theme.SmartACTheme

@Composable
fun BottomControls(
    isTurnedOn: Boolean = true,
    isCoolOn: Boolean = true,
    isFanOn: Boolean = true,
    toggleOn: () -> Unit = {},
    toggleCool: () -> Unit = {},
    toggleFan: () -> Unit = {}
) {
    // UI
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ControlButton(
            iconId = R.drawable.ic_power,
            color = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.onPrimary,
            enabled = true,
            onClick = toggleOn
        )
        ControlButton(
            iconId = R.drawable.ic_cool,
            color = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.onPrimary,
            enabled = true,
            onClick = toggleCool
        )
        ControlButton(
            iconId = R.drawable.ic_fan,
            color = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.onPrimary,
            enabled = true,
            onClick = toggleFan
        )
    }
}


@Composable
fun ControlButton(
    iconId: Int = R.drawable.ic_cool,
    color: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    // UI
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(
                backgroundColor.copy(alpha = 0.1F)
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
            tint = color
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

@Preview
@Composable
fun ControlButtonPreview() {
    SmartACTheme {
        ControlButton()
    }
}
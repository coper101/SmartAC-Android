package com.darealreally.smartac.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.darealreally.smartac.ui.home.components.BottomControls
import com.darealreally.smartac.ui.home.components.TempDisplay
import com.darealreally.smartac.ui.home.components.TempSlider
import com.darealreally.smartac.ui.theme.SmartACTheme

@Composable
fun SmartACApp(
    appState: AppState = rememberAppState()
) {
    // Props

    // Side Effects
//    LaunchedEffect()

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primary
                    )
                )
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Row 1: TEMP DISPLAY
        TempDisplay(
            temperature = appState.temperature,
            setTempUnit = appState.setTempUnit,
            isTurnedOn = appState.isTurnedOn
        )

        // Row 2: TEMP SLIDER
        TempSlider(
            setKelvinValue = appState.setKelvinValue,
            isTurnedOn = appState.isTurnedOn
        )

        // Row 3: BOTTOM CONTROLS
        BottomControls(
            isTurnedOn = appState.isTurnedOn,
            toggleCool = appState.toggleCool,
            toggleFan = appState.toggleFan,
            toggleOn = appState.togglePower
        )
    }
}


/**
 * Preview Section
 */
@Preview
@Composable
fun SmartACAppPreview() {
    SmartACTheme {
        SmartACApp(rememberAppState())
    }
}
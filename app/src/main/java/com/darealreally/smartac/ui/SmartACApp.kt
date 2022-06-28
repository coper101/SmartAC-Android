package com.darealreally.smartac.ui

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.darealreally.smartac.data.TempConstant
import com.darealreally.smartac.ui.components.*
import com.darealreally.smartac.ui.theme.SmartACTheme
import com.darealreally.smartac.utils.kelvinToCelsius
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun SmartACApp(
    appState: AppState = rememberAppState()
) {
    Log.d("App", "SmartACApp called")

    // Props
    val bottomInsetPx = LocalWindowInsets.current.systemBars.bottom
    val bottomInsetDp = with(LocalDensity.current) { bottomInsetPx.toDp() }

    val tempKelvinValue = appState.temperature.kelvinValue
    val isTurnedOn = appState.isTurnedOn
    val appTheme = appState.appTheme
    val theme = appTheme.theme

    // Animation
    val animationSpec: AnimationSpec<Color> = remember {
        spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    }
    val background: Color by animateColorAsState(appTheme.background, animationSpec)
    val lightBackground: Color by animateColorAsState(appTheme.lightBackground, animationSpec)

    // Functions
    fun changeThemeAccordingToTemp(kelvinValue: Double) {
        val tempCelsius = kelvinValue.kelvinToCelsius()
        val minTempCelsius = TempConstant.minTempCelsius
        val maxTempCelsius = TempConstant.maxTempCelsius
        val halfTempDifferenceCelsius = TempConstant.tempDifferentCelsius / 2

        val coldRange = minTempCelsius..(minTempCelsius + halfTempDifferenceCelsius)
        val hotRange = (maxTempCelsius - (halfTempDifferenceCelsius - 1))..maxTempCelsius

        if (hotRange.contains(tempCelsius) && theme != Theme.Hot) {
            // Log.d("SmartACApp", "change to hot")
            appState.setAppTheme(Theme.Hot)
        } else if (coldRange.contains(tempCelsius) && theme != Theme.Cold) {
            // Log.d("SmartACApp", "change to cold")
            appState.setAppTheme(Theme.Cold)
        }

//        Log.d("SmartACApp",
//            """
//                tempCelsius: $tempCelsius
//                coldRange: $coldRange
//                hotRange: $hotRange
//                isTurnedOn: $isTurnedOn
//                appTheme: $theme
//
//                """.trimIndent()
//        )
    }

    // Side Effects
    LaunchedEffect(isTurnedOn) {
        Log.d("SmartACApp", "change isTurnedOn: $isTurnedOn")

        // change theme when AC is powered off or on
        if (!isTurnedOn) {
            appState.setAppTheme(Theme.Inactive)
            Log.d("SmartACApp", "change to inactive")
            return@LaunchedEffect
        }
        changeThemeAccordingToTemp(tempKelvinValue)
    }

    LaunchedEffect(tempKelvinValue) {
        Log.d("SmartACApp", "change tempKelvinValue: $tempKelvinValue")

        // prevent change of theme to cold or hot when its powered off
        if (!isTurnedOn) {
            return@LaunchedEffect
        }
        changeThemeAccordingToTemp(tempKelvinValue)
    }

    LaunchedEffect(theme) {
        Log.d("SmartACApp", "change theme: $theme")
        appState.setSystemBarColor(theme == Theme.Inactive)
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        background,
                        lightBackground
                    )
                )
            ),
    ) {

        // Layer 1: SMOKE
        AnimatedVisibility(
            visible = isTurnedOn && appState.isFanOn,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally { -it }
        ) {
            Smoke(modifier = Modifier.zIndex(0F))
        }

        // Layer 2: SNOWBALLS
        AnimatedVisibility(
            visible = isTurnedOn && theme == Theme.Cold && appState.isCoolOn,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            SnowBalls(modifier = Modifier.zIndex(1F))
        }

        // Layer 3: CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1F))

            // Row 1: TEMP DISPLAY
            TempDisplay(
                temperature = appState.temperature,
                setTempUnit = appState.setTempUnit,
                isTurnedOn = isTurnedOn,
                appTheme = appTheme
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Row 2: TEMP SLIDER
            TempSlider(
                setKelvinValue = appState.setKelvinValue,
                isTurnedOn = isTurnedOn,
                appTheme = appTheme
            )

            Spacer(modifier = Modifier.weight(1F))

            // Row 3: BOTTOM CONTROLS
            BottomControls(
                isTurnedOn = isTurnedOn,
                toggleCool = appState.toggleCool,
                toggleFan = appState.toggleFan,
                toggleOn = appState.togglePower,
                appTheme = appTheme
            )

            Spacer(modifier = Modifier.height(bottomInsetDp + 18.dp))

        } //: Column

    } //: Box
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
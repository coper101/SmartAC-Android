package com.darealreally.smartac.ui

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.darealreally.smartac.data.TempConstant
import com.darealreally.smartac.data.TempUnit
import com.darealreally.smartac.data.Temperature
import com.darealreally.smartac.ui.theme.ThemeColors
import com.darealreally.smartac.utils.celsiusToKelvin
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

enum class Theme {
    Inactive,
    Hot,
    Cold
}

class AppTheme(
    initialTheme: Theme
) {
    var theme by mutableStateOf(initialTheme)

    val background: Color
        get() = when (theme) {
            Theme.Inactive -> ThemeColors.primary
            Theme.Hot -> ThemeColors.hotBackground
            Theme.Cold -> ThemeColors.coldBackground
        }

    val lightBackground: Color
        get() = when (theme) {
            Theme.Inactive -> ThemeColors.primary
            Theme.Hot -> ThemeColors.hotLightBackground
            Theme.Cold -> ThemeColors.coldLightBackground
        }

    val onPrimary: Color
        get() = when (theme) {
            Theme.Inactive -> ThemeColors.onPrimary
            Theme.Hot -> ThemeColors.primary
            Theme.Cold -> ThemeColors.primary
        }
}

class AppState(
    uiController: SystemUiController,
    temperatureState: MutableState<Temperature>,
    isTurnedOnState: MutableState<Boolean>,
    isCoolOnState: MutableState<Boolean>,
    isFanOnState: MutableState<Boolean>,
    appThemeState: MutableState<AppTheme>
) {
    var temperature by temperatureState
    val setTempUnit: (TempUnit) -> Unit = { temperature = temperature.copy(unit = it) }
    val setKelvinValue: (Double) -> Unit = { temperature = temperature.copy(kelvinValue = it) }

    var isTurnedOn by isTurnedOnState
    val togglePower: () -> Unit = { isTurnedOn = !isTurnedOn }

    var isCoolOn by isCoolOnState
    val toggleCool: () -> Unit = { isCoolOn = !isCoolOn }

    var isFanOn by isFanOnState
    val toggleFan: () -> Unit = { isFanOn = !isFanOn }

    val appTheme: AppTheme by appThemeState
    val setAppTheme: (Theme) -> Unit = { appTheme.theme = it }

    val setSystemBarColor: (darkIcons: Boolean) -> Unit = { darkIcons ->
        uiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkIcons,
            isNavigationBarContrastEnforced = false
        )
    }
}

@Composable
fun rememberAppState(
    uiController: SystemUiController = rememberSystemUiController(),
    temperature: MutableState<Temperature> = remember {
        mutableStateOf(
            Temperature(
                kelvinValue = TempConstant.minTempCelsius.celsiusToKelvin(),
                unit = TempUnit.Celsius
            )
        )
    },
    isTurnedOn: MutableState<Boolean> = remember { mutableStateOf(false) },
    isCoolOn: MutableState<Boolean> = remember { mutableStateOf(false) },
    isFanOn: MutableState<Boolean> = remember { mutableStateOf(false) },
    appThemeState: MutableState<AppTheme> = remember { mutableStateOf(AppTheme(Theme.Inactive)) },
) = remember(
    uiController,
    temperature,
    isTurnedOn,
    isCoolOn,
    isFanOn,
    appThemeState
) {
    AppState(
        uiController,
        temperature,
        isTurnedOn,
        isCoolOn,
        isFanOn,
        appThemeState
    )
}
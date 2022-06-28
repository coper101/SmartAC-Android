package com.darealreally.smartac.data

import androidx.compose.ui.geometry.Offset
import com.darealreally.smartac.ui.AppTheme
import com.darealreally.smartac.ui.Theme

object TestData {
    val temp = Temperature(290.15, TempUnit.Celsius)
    val appThemeInactive = AppTheme(Theme.Inactive)
    val appThemeCold = AppTheme(Theme.Cold)
    val appThemeHot = AppTheme(Theme.Hot)
    val snowBallOffset =  Offset(x = 30F, y = 90F)

}
package com.darealreally.smartac.data

import com.darealreally.smartac.utils.celsiusToKelvin
import com.darealreally.smartac.utils.kelvinToCelsius
import com.darealreally.smartac.utils.kelvinToFahrenheit

enum class TempUnit {
    Fahrenheit {
        override var unitSymbol = "°F"
    },
    Celsius {
        override var unitSymbol = "°C"
    };
    abstract var unitSymbol: String
}

object TempConstant {
    val minTempCelsius: Double = 16.0
    val maxTempCelsius: Double = 30.0
    val tempDifferentCelsius: Double = 14.0
}

data class Temperature(
    var kelvinValue: Double,
    var unit: TempUnit,
) {
    val convertedValue: Double
        get() = when (unit) {
            TempUnit.Celsius -> kelvinValue.kelvinToCelsius()
            TempUnit.Fahrenheit -> kelvinValue.kelvinToFahrenheit()
        }
}
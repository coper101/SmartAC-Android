package com.darealreally.smartac.utils

fun Double.kelvinToFahrenheit() = (this - 273.15) * 9/5 + 32
fun Double.kelvinToCelsius() = this - 273.15
fun Double.celsiusToKelvin() = this + 273.15

package com.androidplay.services.utils

import com.androidplay.services.utils.Constants.KELVIN_CONSTANT
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 14,March,2022
 */
object Extensions {

    fun Double.toCelsius(): String {
        val tempInCelcius = this - KELVIN_CONSTANT
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.CEILING
        return "${df.format(tempInCelcius)}°C"
    }
}
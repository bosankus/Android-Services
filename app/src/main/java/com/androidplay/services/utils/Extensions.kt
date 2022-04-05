package com.androidplay.services.utils

import com.androidplay.services.utils.Constants.KELVIN_CONSTANT
import kotlin.math.roundToInt

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 14,March,2022
 */
object Extensions {

    fun Double.toCelsius(): String = "${(this - KELVIN_CONSTANT).roundToInt()}°C"
}
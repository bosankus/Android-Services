package com.androidplay.services.utils

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.androidplay.services.utils.Constants.KELVIN_CONSTANT
import java.math.RoundingMode
import java.text.DecimalFormat

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

    fun Activity.hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus
        if (view == null) view = View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}
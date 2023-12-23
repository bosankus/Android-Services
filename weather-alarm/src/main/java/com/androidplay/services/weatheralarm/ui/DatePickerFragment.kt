package com.androidplay.services.weatheralarm.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import java.util.Calendar

internal class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: WeatherAlarmViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            this,
            year,
            month.plus(1),
            day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.setDate("Selected date: day=$day, month=${month.plus(1)}, year=$year")
    }
}
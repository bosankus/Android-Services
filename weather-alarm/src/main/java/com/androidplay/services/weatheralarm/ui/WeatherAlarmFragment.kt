package com.androidplay.services.weatheralarm.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.androidplay.services.weatheralarm.databinding.FragmentWeatherAlarmBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class WeatherAlarmFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: WeatherAlarmViewModel by activityViewModels { viewModelFactory }

    private var binding: FragmentWeatherAlarmBinding? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherAlarmBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setObservers()
    }

    private fun setClickListeners() {
        binding?.apply {
            fragmentWeatherAlarmDateTxt.setOnClickListener { openDatePickerFragment() }
            fragmentWeatherAlarmTimeTxt.setOnClickListener { openTimePickerFragment() }
            fragmentWeatherAlarmSaveBtn.setOnClickListener { viewModel.performClickEvent() }
        }
    }

    private fun setObservers() {
        viewModel.selectedTime.observe(viewLifecycleOwner) {
            Log.d("Weather-alarm", "setTimeObserver: $it")
        }
        viewModel.selectedDate.observe(viewLifecycleOwner) {
            Log.d("Weather-alarm", "setDateObserver: $it")
        }
    }

    private fun openDatePickerFragment() {
        DatePickerFragment().show(parentFragmentManager, "date_picker")
    }

    private fun openTimePickerFragment() {
        TimePickerFragment().show(parentFragmentManager, "time_picker")
    }

    // preventing memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        // clearing shared viewModel
        requireActivity().viewModelStore.clear()
        // clearing binding references
        binding = null
    }
}
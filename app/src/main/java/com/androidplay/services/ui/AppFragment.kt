package com.androidplay.services.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androidplay.services.BaseContract
import com.androidplay.services.R
import com.androidplay.services.databinding.FragmentAppBinding
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.persistance.DataStoreManager
import com.androidplay.services.model.repository.FeatureToggler
import com.androidplay.services.utils.Constants
import com.androidplay.services.utils.Constants.ALARM_SCHEDULE_FLAG
import com.androidplay.services.utils.Constants.FETCH_TEMPERATURE_FLAG
import com.androidplay.services.utils.Constants.FOREGROUND_SERVICE_FLAG
import com.androidplay.services.utils.Extensions.hideSoftKeyboard
import com.androidplay.services.utils.Extensions.parcelable
import com.androidplay.services.utils.Extensions.toCelsius
import com.androidplay.services.weatheralarm.ui.WeatherAlarmFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppFragment : Fragment(), BaseContract.View {

    private var binding: FragmentAppBinding? = null
    private val weatherDataKey = "weather_data"
    private var weatherData: Weather = Weather(0, Weather.Main())

    @Inject
    lateinit var presenter: BaseContract.Presenter

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attach(this)

        collectInitialData(savedInstanceState)

        setClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(weatherDataKey, weatherData)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.parcelable<Weather>(weatherDataKey)?.let { setSuccessData(it) }
    }

    override fun onResume() {
        super.onResume()
        checkFeatureAvailability(FETCH_TEMPERATURE_FLAG, binding?.appFragmentFetchTemperatureLayout)
        checkFeatureAvailability(ALARM_SCHEDULE_FLAG, binding?.appFragmentAlarmScheduleLayout)
        checkFeatureAvailability(FOREGROUND_SERVICE_FLAG, binding?.appFragmentForegroundServiceLayout
        )
    }

    private fun checkFeatureAvailability(
        key: String,
        view: View?,
    ) {
        when {
            FeatureToggler.isFeatureAvailable(key) -> {
                view?.visibility = View.VISIBLE
            }

            else -> {
                view?.visibility = View.GONE
            }
        }
    }

    private fun collectInitialData(bundle: Bundle?) {
        if (bundle != null) {
            bundle.parcelable<Weather>(weatherDataKey)?.let { setSuccessData(it) }
            return
        }
        lifecycleScope.launch {
            dataStoreManager.getAreaName()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .catch { e ->
                    setFailureData("${e.message}")
                }
                .collect { areaName ->
                    areaName?.let { fetchTemperatureData(it) }
                        ?: fetchTemperatureData(Constants.DEFAULT_AREA)
                }
        }
    }

    private fun setClickListener() {
        binding?.apply {
            appFragmentFetchTemperatureBtFetch.setOnClickListener {
                val providedAreaName = appFragmentFetchTemperatureBtFetch.text.toString().trim()
                fetchTemperatureData(providedAreaName)
            }

            appFragmentBtAlarmSchedule.setOnClickListener {
                val fragment = WeatherAlarmFragment()
                parentFragmentManager.beginTransaction()
                    .replace((requireView().parent as ViewGroup).id, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun fetchTemperatureData(areaName: String?) {
        if (areaName.isNullOrBlank() || areaName == "")
            Toast.makeText(
                this.requireContext(),
                resources.getString(R.string.empty_area_name_text),
                Toast.LENGTH_SHORT
            ).show()
        else presenter.getData(areaName)
    }

    override fun saveDataInDataStore(weather: Weather) {
        lifecycleScope.launch { dataStoreManager.saveData(weather) }
    }

    override fun setSuccessData(weather: Weather) {
        weatherData = weather
        binding?.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    appFragmentFetchTemperatureTemp.text = weather.main.temp.toCelsius()
                    appFragmentFetchTemperatureCityName.text = weather.name
                }
            }
        }
    }

    override fun setFailureData(error: String) {
        binding?.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    appFragmentFetchTemperatureTemp.text =
                        resources.getString(R.string.default_text)
                    appFragmentFetchTemperatureCityName.text = error
                }
            }
        }
    }

    override fun showProgress() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding?.appFragmentFetchTemperatureProgress?.show()
            }
        }
    }

    override fun hideProgress() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding?.appFragmentFetchTemperatureProgress?.hide()
            }
        }
    }

    override fun hideKeyboard() {
        this.requireActivity().hideSoftKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

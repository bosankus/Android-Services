package com.androidplay.services

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.persistance.DataStoreManager
import com.androidplay.services.utils.Constants.DEFAULT_AREA
import com.androidplay.services.utils.Extensions.hideSoftKeyboard
import com.androidplay.services.utils.Extensions.parcelable
import com.androidplay.services.utils.Extensions.toCelsius
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BaseContract.View {

    private var binding: ActivityMainBinding? = null
    private val weatherDataKey = "weather_data"
    private var weatherData: Weather = Weather(0, Weather.Main())

    @Inject
    lateinit var presenter: BaseContract.Presenter

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        (application as WeatherifyMVPApplication).weatherifyMVPComponent.inject(this)

        presenter.attach(this)

        collectInitialData(savedInstanceState)

        setClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(weatherDataKey, weatherData)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.parcelable<Weather>(weatherDataKey)?.let { setSuccessData(it) }
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
                    areaName?.let { fetchTemperatureData(it) } ?: fetchTemperatureData(DEFAULT_AREA)
                }
        }
    }

    private fun setClickListener() {
        binding?.apply {
            activityMainBtFetch.setOnClickListener {
                val providedAreaName = activityMainEtAreaName.text.toString().trim()
                fetchTemperatureData(providedAreaName)
            }
        }
    }

    override fun fetchTemperatureData(areaName: String?) {
        if (areaName.isNullOrBlank() || areaName == "")
            Toast.makeText(
                this,
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
                    activityMainTemperature.text = weather.main.temp.toCelsius()
                    activityMainCityName.text = weather.name
                }
            }
        }
    }

    override fun setFailureData(error: String) {
        binding?.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    activityMainTemperature.text = resources.getString(R.string.default_text)
                    activityMainCityName.text = error
                }
            }
        }
    }

    override fun showProgress() {
        binding?.activityMainProgress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding?.activityMainProgress?.visibility = View.INVISIBLE
    }

    override fun hideKeyboard() {
        this.hideSoftKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
package com.androidplay.services.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidplay.services.WeatherifyMVPApplication
import com.androidplay.services.BaseContract
import com.androidplay.services.R
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.model.model.Weather
import com.androidplay.services.utils.Constants.DEFAULT_AREA
import com.androidplay.services.utils.Extensions.toCelsius
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BaseContract.View {

    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var presenter: BaseContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        (application as WeatherifyMVPApplication).weatherifyMVPComponent.inject(this)

        presenter.attach(this)

        // calling fetch data method using default location
        fetchTemperatureData(DEFAULT_AREA)

        setClickListener()
    }

    override fun setClickListener() {
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

    override fun setSuccessData(weather: Weather) {
        lifecycleScope.launchWhenStarted {
            binding?.apply {
                activityMainTemperature.text =  weather.main.temp.toCelsius()
                activityMainCityName.text = weather.name
            }
        }
    }

    override fun setFailureData(error: String) {
        binding?.apply {
            activityMainTemperature.text = resources.getString(R.string.default_text)
            activityMainCityName.text = error
        }
    }

    override fun showProgress() {
        binding?.activityMainProgress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        lifecycleScope.launchWhenStarted {
            binding?.activityMainProgress?.visibility = View.INVISIBLE
        }
    }

    override fun hideKeyboard() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
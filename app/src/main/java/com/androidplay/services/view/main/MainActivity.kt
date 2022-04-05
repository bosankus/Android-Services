package com.androidplay.services.view.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidplay.services.WeatherifyMVPApplication
import com.androidplay.services.BaseContract
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.model.model.Weather
import com.androidplay.services.utils.Extensions.toCelsius
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BaseContract.View {

    private var binding: ActivityMainBinding? = null
    /*private lateinit var presenter: MainPresenterImpl*/
    @Inject lateinit var presenter: BaseContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
            
        (application as WeatherifyMVPApplication).weatherifyMVPComponent.inject(this)

        // Initializing presenter here
        /*presenter = MainPresenterImpl(interactor = MainInteractor())*/
        
        presenter.attach(this)

        // calling fetch data method
        presenter.getData("Bengaluru")
    }

    override fun setSuccessData(weather: Weather) {
        lifecycleScope.launchWhenStarted {
            binding?.apply {
                activityMainTemperature.text = weather.main?.temp?.toCelsius()
                activityMainCityName.text = weather.name
            }
        }
    }

    override fun setFailureData(error: String) {
        binding?.activityMainCityName?.text = error
    }

    override fun showProgress() {
        binding?.activityMainProgress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        lifecycleScope.launchWhenStarted { binding?.activityMainProgress?.visibility = View.GONE }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
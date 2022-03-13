package com.androidplay.services.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.presenter.MainInteractor
import com.androidplay.services.presenter.MainPresenter
import com.androidplay.services.presenter.MainView
import com.androidplay.services.model.model.Weather

class MainActivity : AppCompatActivity(), MainView {

    private var binding: ActivityMainBinding? = null
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initializing presenter here
        presenter = MainPresenter(this, MainInteractor())

        // calling fetch data method
        presenter.getData()
    }

    override fun setData(weather: Weather) {
        lifecycleScope.launchWhenStarted {
            binding?.apply {
                activityMainTemperature.text = weather.main?.temp.toString()
                activityMainCityName.text = weather.name
            }
        }
    }

    override fun setFailureError(error: String) {
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
        presenter.onDestroy()
    }
}
package com.androidplay.services.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidplay.services.contract.MainContract
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.Interactor.MainInteractor
import com.androidplay.services.presenter.MainPresenter
import com.androidplay.services.model.model.Weather

class MainActivity : AppCompatActivity(), MainContract.View {

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

    override fun setSuccessData(weather: Weather) {
        lifecycleScope.launchWhenStarted {
            binding?.apply {
                activityMainTemperature.text = weather.main?.temp.toString()
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
        presenter.onDestroy()
    }
}
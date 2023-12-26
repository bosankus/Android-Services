package com.androidplay.services.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidplay.services.databinding.ActivityMainBinding
import com.androidplay.services.model.repository.FeatureToggler

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureToggler.initFeatureToggleInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
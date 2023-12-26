package com.androidplay.services.model.repository

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

object FeatureToggler {

    fun initFeatureToggleInstance() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("remote_cofig", "Remote config params updated: ${task.result}")
            } else {
                Log.d("remote_cofig", "Remote config params failed to update")
            }
        }
    }

    fun isFeatureAvailable(key: String): Boolean {
        return Firebase.remoteConfig.getBoolean(key)
    }
}
package com.androidplay.services.utils.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.androidplay.services.utils.Constants.ALARM_INTENT_ACTION
import com.androidplay.services.utils.Constants.ALARM_INTENT_TEXT

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null &&
            (intent != null &&
                    (intent.action == ALARM_INTENT_ACTION ||
                            intent.action == "android.intent.action.BOOT_COMPLETED"))
        ) {
            val alarmText = intent.extras?.getString(ALARM_INTENT_TEXT)

            // TODO: Showing toast now, but in real should :
            // 1. Make network call using MainInteractor, and then,
            // 2. trigger notification with the weather date,
            // 3. Or else trigger weather data fetching failed notification.
            Toast.makeText(context, alarmText, Toast.LENGTH_LONG).show()
        }
    }
}
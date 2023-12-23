package com.androidplay.services.utils.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.androidplay.services.utils.Constants.ALARM_CONTROLLER_REQ_CODE
import com.androidplay.services.utils.Constants.ALARM_INTENT_ACTION
import com.androidplay.services.utils.Constants.ALARM_INTENT_TEXT

object AlarmController {

    fun Context.setAlarm(alarmManager: AlarmManager) {
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            action = ALARM_INTENT_ACTION
            putExtra(ALARM_INTENT_TEXT, "This is random text to trigger toast")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_CONTROLLER_REQ_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // TODO
        /*alarmManager.setInexactRepeating(

        )*/
    }
}
package com.test.app.cocktail.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.test.app.cocktail.R

class DrinkBroadcast:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        /*val notification = NotificationCompat.Builder(context, "Sofit")
            .setCustomContentView(R.layout.item_drinks)
            .build()
       val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(101,notification)*/

    }
}
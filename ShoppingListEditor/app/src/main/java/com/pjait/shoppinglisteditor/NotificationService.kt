package com.pjait.shoppinglisteditor

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {
    private val channelId = "channel1"
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("newItem")
//        val launchIntent = packageManager.getLaunchIntentForPackage("com.pjait.shopping.ui.shoppingList")
        val notificationIntent = Intent().also {
            it.component = ComponentName("com.pjait.shopping.ui.shoppingList", "ProductListActivity")
            it.putExtra("newItem", input)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_ONE_SHOT
        )
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("New Item Added !!")
            .setContentText("Item Name: $input")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(1, notification)
        return START_NOT_STICKY
    }
}

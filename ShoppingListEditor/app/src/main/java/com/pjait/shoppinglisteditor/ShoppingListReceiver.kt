package com.pjait.shoppinglisteditor

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class ShoppingListReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("broadcast", "received" )
        val input = intent.getStringExtra("newItem")
        Toast.makeText(context, "received broadcast from external app with id: $input", Toast.LENGTH_SHORT).show()
        ContextCompat.startForegroundService(context, intent)
        Log.d("service", "started")
    }
}

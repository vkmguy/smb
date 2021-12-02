package com.pjait.shoppinglisteditor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.ArrayList

class ShoppingListReceiver : BroadcastReceiver() {
    private val channelId = "channel1"
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val itemName = intent.getStringExtra("newItem")
        val productList = intent.getStringArrayListExtra("productList")
        Toast.makeText(context,"Data: $itemName", Toast.LENGTH_SHORT).show()
        val notificationServiceIntent = Intent(context, NotificationService::class.java).also {
            it.putExtra("newItem",itemName)
            it.putStringArrayListExtra("productList", productList)
        }
        context.startService(notificationServiceIntent)
    }
}

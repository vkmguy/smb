package com.pjait.shoppinglisteditor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.pjait.shoppinglisteditor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var shoppingListReceiver: ShoppingListReceiver
    private val channelId = "channel1"
    private val channelName = "notificationChanel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createChannel(this)
    }

    override fun onStart() {
        super.onStart()
        shoppingListReceiver = ShoppingListReceiver()
        val intentFilter = IntentFilter("shopping.ac.ADD_ITEM")
        registerReceiver(shoppingListReceiver, intentFilter)
        Log.d("receiver", "started")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(shoppingListReceiver)
        Log.d("receiver", "stopped")
    }

    private fun createChannel(context: Context){
        val channel =  NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
//            .createNotificationChannel(channel)
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
        Log.d("channel", "created")
    }
}

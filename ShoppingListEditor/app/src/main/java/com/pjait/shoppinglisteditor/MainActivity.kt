package com.pjait.shoppinglisteditor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
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
        shoppingListReceiver = ShoppingListReceiver()
        setContentView(binding.root)
        binding.tvProductName.text  = intent.getStringExtra("newItem").toString()
        createChannel(this, channelId, channelName)
    }

    override fun onStart() {
        super.onStart()
        shoppingListReceiver = ShoppingListReceiver()
        val intentFilter = IntentFilter()
        registerReceiver(shoppingListReceiver, intentFilter)
        Log.d("receiver", "started")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(shoppingListReceiver)
        Log.d("receiver", "stopped")
    }

    private fun createChannel(context: Context, id: String, name: String){
        val channel =  NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
//        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
//            .createNotificationChannel(channel)
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
        Log.d("channel", "created")
    }
}

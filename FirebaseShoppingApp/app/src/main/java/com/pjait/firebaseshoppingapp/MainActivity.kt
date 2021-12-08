package com.pjait.firebaseshoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjait.firebaseshoppingapp.activity.ProductListActivity
import com.pjait.firebaseshoppingapp.databinding.ActivityMainBinding
import com.pjait.firebaseshoppingapp.helper.Constants
import com.pjait.firebaseshoppingapp.model.ShoppingItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.app.setOnClickListener{
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }
}

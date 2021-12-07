package com.pjait.listapplicationfirebase.shoppingList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pjait.listapplicationfirebase.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {

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

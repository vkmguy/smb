package com.pjait.shopping.ui.shoppingList

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.pjait.shopping.R
import com.pjait.shopping.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "OPTIONS"
        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData()

        binding.swMode.setOnClickListener{
            val selectedMode = binding.swMode.isChecked
            val sharedPreferences: SharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.apply(){
                putBoolean("DARK_MODE", selectedMode)
            }.apply()
            Toast.makeText(this, "Dark Mode enabled :$selectedMode", Toast.LENGTH_SHORT).show()
            changeUiMode(selectedMode)
        }

        binding.rbRed.setOnClickListener{
            val textSize = binding.rbRed.text.toString()
            val sharedPreferences: SharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.apply(){
                putString("TEXT_SIZE", textSize)
            }.apply()
            Toast.makeText(this, "Dark Mode enabled :$textSize", Toast.LENGTH_SHORT).show()
            changeTextColor(textSize)
        }

        binding.rbBlue.setOnClickListener{
            val textSize = binding.rbBlue.text.toString()
            val sharedPreferences: SharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.apply(){
                putString("TEXT_SIZE", textSize)
            }.apply()
            Toast.makeText(this, "Dark Mode enabled :$textSize", Toast.LENGTH_SHORT).show()
            changeTextColor(textSize)
        }
    }

    private fun changeUiMode(darkModeEnabled: Boolean) {
        if (darkModeEnabled) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun changeTextColor(textColor: String) {

        var textView = findViewById<TextView>(R.id.tvOptionsPage)

        if (textColor == "Red") textView.setTextColor(Color.parseColor("#ffff0000"))
        else textView.setTextColor(Color.parseColor("#ff0000ff"))
    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        val savedMode = sharedPreferences.getBoolean("DARK_MODE", false);
        val savedTextSize = sharedPreferences.getString("TEXT_SIZE", "red").toString();
        binding.swMode.isChecked = savedMode
        changeUiMode(savedMode)
        changeTextColor(savedTextSize)
    }


}
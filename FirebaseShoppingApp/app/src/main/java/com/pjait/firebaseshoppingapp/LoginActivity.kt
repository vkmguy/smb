package com.pjait.firebaseshoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pjait.firebaseshoppingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btRegister.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                binding.etEmail.text.toString(), binding.etPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("createUserWithEmail:success", it.result.toString())
                    Toast.makeText(
                        this, "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Registration error !", Toast.LENGTH_SHORT).show()
                    Log.e("createUserWithEmail:failure", it.result.toString())
                }
            }
        }

        binding.btLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(), binding.etPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("signInWithEmail:success", it.result.toString())
                    startActivity(Intent(this, MainActivity::class.java).also {
                        it.putExtra("username", mAuth.currentUser?.email.toString())
                    })
                } else {
                    Toast.makeText(this, "Login error !", Toast.LENGTH_SHORT).show()
                    Log.e("signInWithEmail:failure", it.result.toString())
                }
            }
        }
    }
}

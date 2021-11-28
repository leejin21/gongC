package com.example.gongc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.gongc.feature.sign.SignInActivity
import com.example.gongc.feature.sign.SignUpActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSignIn = findViewById<Button>(R.id.button_main_signin)
        val btnSignUp = findViewById<Button>(R.id.button_main_signup)

        btnSignIn.setOnClickListener{
            Log.d("debug11", "main login")
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener{
            Log.d("debug11", "main signup")
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
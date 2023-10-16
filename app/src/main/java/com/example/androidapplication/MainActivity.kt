package com.example.androidapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)


        buttonLogin.setOnClickListener {
            val intent2 = Intent(this@MainActivity, Activity3::class.java)
            startActivity(intent2)
        }

        buttonRegister.setOnClickListener {

        }
    }
}

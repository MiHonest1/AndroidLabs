package com.example.androidapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Home1 : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_1)

        val btn12 = findViewById<Button>(R.id.btn12)
        val btn13 = findViewById<Button>(R.id.btn13)

        val textViewUser = findViewById<TextView>(R.id.textViewUser)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("email", "").toString()
        val password = sharedPreferences.getString("password", "").toString()

        textViewUser.text = "login is $username password is $password"

        btn12.setOnClickListener {
            val intentToActivity3 = Intent(this@Home1, MainActivity::class.java)
            startActivity(intentToActivity3)
        }

        btn13.setOnClickListener {
            val intentToActivity3 = Intent(this@Home1, Activity3::class.java)
            startActivity(intentToActivity3)
        }
    }
}

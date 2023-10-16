package com.example.androidapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        buttonLogin.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val savedEmail = sharedPreferences.getString("email", "")
                val savedPassword = sharedPreferences.getString("password", "")

                if (email == savedEmail && password == savedPassword) {
                    editor.apply()

                    val intent2 = Intent(this@MainActivity, Home1::class.java)
                    startActivity(intent2)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }

        buttonRegister.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()

                Toast.makeText(this@MainActivity, "Регистрация успешна", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

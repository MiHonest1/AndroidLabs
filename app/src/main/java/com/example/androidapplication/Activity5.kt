package com.example.androidapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class Activity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_5)

        val textView5 = findViewById<TextView>(R.id.textView5)
        val btn5 = findViewById<Button>(R.id.btn5)
        val resIntent = intent
        val name = resIntent.getSerializableExtra("name") as? Name // Получаем сериализованный объект Name из Intent

        if (name != null) {
            textView5.text = name.name
        }

        btn5.setOnClickListener {
            val intentToActivity4 = Intent(this@Activity5, Activity4::class.java) // Создаем новый Intent, указывая текущий контекст и целевую активити
            startActivity(intentToActivity4) // Запускаем Activity4 с помощью Intent
        }
    }
}




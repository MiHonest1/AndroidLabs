package com.example.androidapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Activity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)
        val btn = findViewById<Button>(R.id.btn)
        val nextButton3 = findViewById<Button>(R.id.nextButton3)
        val backButton3 = findViewById<Button>(R.id.backButton3)
        val nameText = findViewById<EditText>(R.id.nameText)

        btn.setOnClickListener {
            val name = nameText.text.toString() // Получаем текст из поля ввода текста
            val nameS = Name(name) // Создаем объект Name с полученным именем
            val intent = Intent(this@Activity4, Activity5::class.java) // Создаем новый Intent, указывая текущий контекст и целевую активити
            intent.putExtra("name", nameS) // Помещаем сериализованный объект Name в Intent под ключом "name"
            startActivity(intent)
        }

        nextButton3.setOnClickListener {
            val intent = Intent(this@Activity4, Activity5::class.java)
            startActivity(intent)
        }

        backButton3.setOnClickListener {
            val intent = Intent(this@Activity4, OsnClass::class.java)
            startActivity(intent)
        }
    }
}


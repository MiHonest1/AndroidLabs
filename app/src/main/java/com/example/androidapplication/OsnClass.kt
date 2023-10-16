package com.example.androidapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OsnClass : AppCompatActivity() {
    var counter1 = 0;
    var counter2 = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)

        var text = findViewById<TextView>(R.id.textView2)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val nextButton1 = findViewById<Button>(R.id.nextButton2)
        val backButton1 = findViewById<Button>(R.id.backButton2)

        button1.setOnClickListener {
            button1.text = "Кнопка 1 нажата!"
            counter1++
            text.setText("Кнопка 1 нажата $counter1 раз")
        }

        button2.setOnClickListener {
            button2.text = "Кнопка 2 нажата!"
            counter2++;
            text.setText("Кнопка 2 нажата $counter2 раз")
        }

        nextButton1.setOnClickListener {
            val intent = Intent(this@OsnClass, Activity4::class.java);
            startActivity(intent);

        }

        backButton1.setOnClickListener {
            val intent = Intent(this@OsnClass, Activity3::class.java);
            startActivity(intent);

        }
    }

}

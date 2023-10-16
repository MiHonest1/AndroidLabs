package com.example.androidapplication

import android.os.Bundle
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


    }

}

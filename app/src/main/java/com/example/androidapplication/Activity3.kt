package com.example.androidapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class Activity3 : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dataList: ArrayList<String>
    private lateinit var listV: ListView
    private lateinit var checkedItems: SparseBooleanArray
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val nextButton1 = findViewById<Button>(R.id.nextButton1)
        val backButton1 = findViewById<Button>(R.id.backButton1)
        val button7 = findViewById<Button>(R.id.button7)
        listV = findViewById<ListView>(R.id.dynamic)
        val nameText1 = findViewById<EditText>(R.id.nameText1)
        val button6 = findViewById<Button>(R.id.button6)

        dataList = ArrayList<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, dataList)
        listV.adapter = adapter
        listV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        button7.setOnClickListener {
            val data = nameText1.text.toString().trim()
            dataList.add(data)
            adapter.notifyDataSetChanged()
            nameText1.text.clear()
        }

        button6.setOnClickListener {
            checkedItems = listV.checkedItemPositions
            val selectedItems = ArrayList<String>()

            for (i in 0 until checkedItems.size()) {
                val position = checkedItems.keyAt(i)
                if (checkedItems.valueAt(i)) {
                    selectedItems.add(dataList[position])
                }
            }

            dataList.removeAll(selectedItems)
            adapter.notifyDataSetChanged()
        }

        nextButton1.setOnClickListener {
            val intent3 = Intent(this@Activity3, OsnClass::class.java)
            startActivity(intent3)
        }

        backButton1.setOnClickListener {
            val intent3 = Intent(this@Activity3, Home1::class.java)
            startActivity(intent3)
        }

        val savedData = sharedPreferences.getStringSet("listData", null)
        if (savedData != null) {
            dataList.addAll(savedData)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Логи приложения", "Сообщение отладки для метода onDestroy()")

        val editor = sharedPreferences.edit()
        editor.putStringSet("listData", HashSet(dataList))
        editor.apply()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Логи приложения", "Сообщение отладки для метода onPause()")

        val editor = sharedPreferences.edit()
        editor.putStringSet("listData", HashSet(dataList))
        editor.apply()
    }

    override fun onResume() { //вызывается, когда отрисовывается приложение
        Log.i("Логи приложения", "Вызван метод onResume()")
        super.onResume()
    }

    override fun onStart() {
        Log.w("Логи приложения", "Сообщение предупреждения для метода onStart()")
        super.onStart()
    }

    override fun onStop() {
        Log.v("Логи приложения", "Сообщение подробностей для метода onStop()")
        super.onStop()
    }


}





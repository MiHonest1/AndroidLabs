package com.example.androidapplication

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

class Activity3 : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String> // Адаптер для списка
    private lateinit var dataList: ArrayList<String> // Список данных
    private lateinit var listV: ListView // ListView для отображения данных
    private lateinit var checkedItems: SparseBooleanArray // Массив для отслеживания выбранных элементов
    private lateinit var sharedPreferences: SharedPreferences // Объект для работы с SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val nextButton1 = findViewById<Button>(R.id.nextButton1)
        val backButton1 = findViewById<Button>(R.id.backButton1)
        val button7 = findViewById<Button>(R.id.button7)
        listV = findViewById<ListView>(R.id.dynamic)
        val nameText1 = findViewById<EditText>(R.id.nameText1)
        val button6 = findViewById<Button>(R.id.button6)

        // Инициализируем список данных и адаптер для ListView
        dataList = ArrayList<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, dataList)
        listV.adapter = adapter
        listV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        // Получаем экземпляр SharedPreferences с именем "MyPrefs" и режимом Context.MODE_PRIVATE
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        button7.setOnClickListener {
            // Получаем значение из текстового поля nameText1 и преобразуем его в строку, удаляя начальные и конечные пробелы
            val data = nameText1.text.toString().trim()
            // Добавляем значение в список данных
            dataList.add(data)
            // Уведомляем адаптер об изменении данных
            adapter.notifyDataSetChanged()
            // Очищаем текстовое поле nameText1
            nameText1.text.clear()
        }

        button6.setOnClickListener {
            // Получаем массив выбранных элементов из ListView
            checkedItems = listV.checkedItemPositions
            // Создаем список для хранения выбранных элементов
            val selectedItems = ArrayList<String>()

            // Проходим по всем элементам массива checkedItems
            for (i in 0 until checkedItems.size()) {
                // Получаем позицию элемента
                val position = checkedItems.keyAt(i)
                // Проверяем, выбран ли элемент
                if (checkedItems.valueAt(i)) {
                    // Если элемент выбран, добавляем его в список выбранных элементов
                    selectedItems.add(dataList[position])
                }
            }

            // Удаляем выбранные элементы из списка данных
            dataList.removeAll(selectedItems)
            // Уведомляем адаптер об изменении данных
            adapter.notifyDataSetChanged()
        }

        nextButton1.setOnClickListener {
            val intent3 = Intent(this@Activity3, OsnClass::class.java)
            startActivity(intent3)
        }

        backButton1.setOnClickListener {
            val intent3 = Intent(this@Activity3, Authorization::class.java)
            startActivity(intent3)
        }

        // Получаем сохраненные данные из SharedPreferences для ключа "listData"
        val savedData = sharedPreferences.getStringSet("listData", null)
        // Если данные не равны null, добавляем их в список данных
        if (savedData != null) {
            dataList.addAll(savedData)
        }
    }

    override fun onDestroy() { // Вызывается при уничтожении активити
        super.onDestroy()
        Log.d("Логи приложения", "Сообщение отладки для метода onDestroy()")

        // Получаем экземпляр эдитора SharedPreferences
        val editor = sharedPreferences.edit()
        // Сохраняем список данных в SharedPreferences для ключа "listData"
        editor.putStringSet("listData", HashSet(dataList))
        // Применяем изменения в SharedPreferences
        editor.apply()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Логи приложения", "Сообщение отладки для метода onPause()")

        val editor = sharedPreferences.edit()
        editor.putStringSet("listData", HashSet(dataList))
        editor.apply()
    }

    override fun onResume() { // Вызывается, когда отрисовывается приложение
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





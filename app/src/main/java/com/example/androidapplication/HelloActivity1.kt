package com.example.androidapplication

import android.Manifest
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class HelloActivity1 : AppCompatActivity() {
    private lateinit var stopButton: Button
    private lateinit var startButton: Button
    private lateinit var volumeText: TextView
    private val recording = Recording(this) // создаем recording с текущим контекстом
    private var countDownTimer: CountDownTimer? = null // объявляем таймер countDownTimer и присваиваем ему значение null
    private var minVolume = Int.MAX_VALUE // объявляем переменную minVolume и присваиваем ей максимальное значение Int.MAX_VALUE
    private var maxVolume = Int.MIN_VALUE // объявляем переменную maxVolume и присваиваем ей минимальное значение Int.MIN_VALUE
    private var summ = 0
    private var counter = 0
    private lateinit var min: TextView
    private lateinit var avg: TextView
    private lateinit var max: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact1)

        ActivityCompat.requestPermissions( //запрос доступа к микрофону
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            1
        )

        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        volumeText = findViewById(R.id.volumeText)

        min = findViewById(R.id.min) // инициализируем minText
        avg = findViewById(R.id.avg) // инициализируем avgText
        max = findViewById(R.id.max) // инициализируем maxText

        startButton.setOnClickListener {//начинаем запись
            if (recording.isRecording()) { // проверяем, запись идёт или нет
                return@setOnClickListener // если запись идет, ничего не делаем
            }

            recording.start() // запускаем запись
            countDownTimer = object : CountDownTimer(
                300000, // время работы
                100 // каждые 100 мс забираем громкость с микрофона
            ) { // создаём таймер (работаем минуту, каждые 100 мс берём данные с микрофона)
                override fun onTick(p0: Long) { //каждые 100 мс
                    val volume = recording.getDB() // берём значение шума с микрофона

                    // если значение шума не равно Int.MIN_VALUE, то обновляем значения minVolume, maxVolume, summ и counter
                    if (volume.toInt() != Int.MIN_VALUE) {
                        volumeText.text = "${volume.toInt()} дБ" // выводим значение шума в TextView
                        // обновляем минимальное, максимальное значение, а также значение счётчика и суммы значений
                        if (volume < minVolume) {
                            minVolume = volume.toInt()
                        }
                        if (volume > maxVolume) {
                            maxVolume = volume.toInt()
                        }
                        summ += volume.toInt()
                        counter++
                    }
                }

                override fun onFinish() {

                }
            }
            countDownTimer?.start() // запускаем таймер
        }

        stopButton.setOnClickListener {//останавливаем запись
            if (!recording.isRecording()) { // проверяем, запись идёт или нет
                return@setOnClickListener // если запись не идет, ничего не делаем
            }

            recording.stop() // останавливаем запись
            countDownTimer?.cancel() // останавливаем таймер, если останавливаем запись
            countDownTimer = null // зануляем таймер

            volumeText.text = "0 дБ" // устанавливаем значение в ноль

            val averageVolume = summ / counter // вычисляем среднее значение шума

            min.text = if (minVolume != Int.MAX_VALUE) "$minVolume" else "0 дБ"  // устанавливаем минимальное значение в minText
            avg.text = if (counter != 0) "$averageVolume" else "0 дБ"  // устанавливаем среднее значение в avgText
            max.text = if (maxVolume != Int.MIN_VALUE) "$maxVolume" else "0 дБ"  // устанавливаем максимальное значение в maxText

            minVolume = Int.MAX_VALUE
            maxVolume = Int.MIN_VALUE
            summ = 0
            counter = 0
        }

    }
}

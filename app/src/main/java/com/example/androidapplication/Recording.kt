package com.example.androidapplication

import android.content.Context
import android.media.MediaRecorder
import kotlin.math.log10

//класс Recording используется для записи аудио
class Recording(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null //для записи аудио

    private val referenceAmplitude = 1.0 // опорное значение амплитуды, используемое для вычисления уровня громкости

    //метод start() создает объект MediaRecorder,
    //настраивает его для записи аудио с микрофона в формате AAC_ADTS
    //кодек AAC
    //записываемый файл сохраняется по указанному пути
    //после этого происходит подготовка к записи и старт записи
    fun start(){
        val mediaRecorder = MediaRecorder() //создаём медиарекордер

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC) // источник аудио - микрофон
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS) //формат записи файла
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // формат записи аудио (кодек)
        mediaRecorder.setOutputFile(getPath()) // путь, куда будет записан окончательный аудиофайл
        mediaRecorder.prepare() // подготовка к записи аудио
        mediaRecorder.start() // старт записи

        this.mediaRecorder = mediaRecorder
    }

    //завершает запись аудио и освобождает медиаресурсы
    fun stop(){
        mediaRecorder?.stop() // завершаем медиарекордер
        mediaRecorder?.release() // освобождаем медиаресурсы, занятые медиарекордером
        mediaRecorder = null
    }

    //возвращает текущее значение уровня громкости в децибелах
    //значение вычисляется на основе максимальной амплитуды, полученной с микрофона, и опорного значения амплитуды
    fun getDB(): Double{ //максимальное значение амплитуды в наст время
        val amplitude = mediaRecorder?.maxAmplitude ?: 0 //возвращает максимальное значение амплитуды в последней порции записанного звука
        return 20 * log10(amplitude / referenceAmplitude) // переводим в децибелы с помощью функции log10
    }

    //возвращает true, если запись аудио в данный момент происходит, и false в противном случае
    fun isRecording(): Boolean{ // состояние записи или отсутствия записи
        return mediaRecorder != null
    }

    //возвращает путь до папки, где будет сохранен окончательный аудиофайл
    //путь формируется из пути к внешнему кэшу приложения
    private fun getPath(): String{ // путь в виде строки
        return "${context.externalCacheDirs[0].absolutePath}/audioFile.wav" // путь до папки
    }

}

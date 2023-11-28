package com.example.androidapplication

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import android.widget.Toast


class DatabaseThread(private val db: DatabaseHandler) : Thread() {

    private lateinit var handlerDBThread: Handler

    // Переопределение метода run для выполнения операций с базой данных
    override fun run() {
        // Создаем Looper для этого потока
        Looper.prepare()

        // Создаем Handler, связанный с этим Looper, для обработки сообщений
        handlerDBThread = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> {
                        val user = msg.obj as User
                        registrUser(user)
                    }

                    2 -> {
                        val user = msg.obj as User
                        passwChange(user)
                    }

                    3 -> {
                        val userLogin = msg.obj as String
                        deleteUser(userLogin)
                    }
                }
            }
        }

        // Запускаем цикл обработки сообщений
        Looper.loop()
    }

    // Метод для получения Handler
    fun getHandler(): Handler {
        return handlerDBThread
    }

    // Метод для отправки сообщения через переданный Handler
    fun doSomething(handlerHelA: Handler) {
        Thread {
            val message = Message.obtain()
            val stroka = "test"
            message.what = 1
            message.obj = stroka
            handlerHelA.sendMessage(message)
        }.start()
    }

    fun logUsers(handlerHelA: Handler) {
        Thread {
            val message = Message.obtain()
            val userList : List<User> = db.getAllUsers()
            message.what = 2
            message.obj = userList
            handlerHelA.sendMessage(message)
        }.start()
    }

    fun registrUser(user: User) {
        Thread {
            db.addUser(user)
        }.start()
    }

    fun passwChange(user: User) {
        Thread {
            // post() для выполнения операции в главном потоке через Handler
            handlerDBThread.post {
                db.PassChangeClick(user)
            }
        }.start()
    }

    fun deleteUser(loginToDelete: String) {
        Thread {
            db.deleteUserFromDB(loginToDelete)
        }.start()
    }


    fun authorization(login: String, pass: String, handlerHelA: Handler) {
        Thread {
            val message = Message.obtain()
            val userDB = db.getUser(login, pass)
            message.obj = userDB
            handlerHelA.sendMessage(message)
        }.start()
    }



}
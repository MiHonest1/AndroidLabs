package com.example.androidapplication

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.EditText


class DatabaseHandler(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
    }
    //создать бд
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, pass TEXT)"
        db!!.execSQL(query)
    }
    //обновить бд
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
    //добавить пользователя в бд
    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("id", user.id)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }
    //получить инфу об одном пользователе из бд
    fun getUser(login: String, pass: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return result.moveToFirst()
    }

    //получить инфу обо всех пользователях из бд
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val selectQuery = "SELECT * FROM users"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
                )
                val login = cursor.getString(
                    cursor.getColumnIndexOrThrow("login")
                )
                val pass = cursor.getString(
                    cursor.getColumnIndexOrThrow("pass")
                )
                val user = User(id = id, login = login, pass = pass)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }


    fun deleteUserFromDB(loginToDelete: String) {
        val db = this.writableDatabase
        db.delete("users", "login=?", arrayOf(loginToDelete))
        db.close()
    }


    fun PassChangeClick(user: User) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Смена пароля")

        // Вводимая инф
        val input = EditText(context)
        builder.setView(input)

        // Кнопки
        builder.setPositiveButton("Сохранить") { dialog, which ->
            val newPassword = input.text.toString()
            // Обновление пароля пользователя в БД
            val dbHandler = DatabaseHandler(context, null)
            val updatedUser = User(user.id, user.login, newPassword)
            dbHandler.updateUser(updatedUser)
        }
        builder.setNegativeButton("Отмена") { dialog, which -> dialog.cancel() }

        builder.show()

    }



    fun updateUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("login", user.login)
        values.put("pass", user.pass)

        db.update("users", values, "id = ?", arrayOf(user.id.toString()))

        db.close()
    }


}

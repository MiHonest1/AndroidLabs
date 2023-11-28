package com.example.androidapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Authorization : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var db: DatabaseHandler
    private lateinit var userLogin: EditText
    private lateinit var userPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        userLogin = findViewById(R.id.user_login_auth)
        userPass = findViewById(R.id.user_pass_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        db = DatabaseHandler(this, null)

        handler = Handler(Looper.getMainLooper()) {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this@Authorization, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }

            val data = it.obj as Boolean
            if (data) {
                Toast.makeText(this@Authorization, "Пользователь $login авторизован", Toast.LENGTH_LONG).show()
                userLogin.text.clear()
                userPass.text.clear()

                val intent2 = Intent(this@Authorization, Activity3::class.java)
                startActivity(intent2)
            } else {
                Toast.makeText(this@Authorization, "Пользователь $login не авторизован", Toast.LENGTH_LONG).show()
            }
            true
        }


        linkToReg.setOnClickListener {
            val intent = Intent(this, HelloActivity1::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()
            DatabaseThread(db).authorization(login, pass, handler)
        }
    }
}

package com.example.androidapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Types.NULL


class HelloActivity1 : AppCompatActivity() {

    companion object {
        private val TAG: String = HelloActivity1::class.java.simpleName
    }

    private lateinit var db: DatabaseHandler

    private lateinit var rvUsers: RecyclerView
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact1)

        db = DatabaseHandler(this, null)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userId: EditText = findViewById(R.id.user_id)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        val btnLoad: Button = findViewById(R.id.btnLoad)

        //ссылка на авторизацию
        linkToAuth.setOnClickListener {
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }

        //регистрация нового пользователя
        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val id = userId.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login.isEmpty() || id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val user = User(id.toInt(), login, pass)
                Thread {
                    db.addUser(user)

                    button.post {
                        Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                    }
                }.start()
            }
        }

        //загрузка инфы из бд
        btnLoad.setOnClickListener {
            Thread {
                val users = db.getAllUsers()
                val usersLog = users.joinToString(separator = ",\n")
                Log.d(TAG, "Users:\n $usersLog")

                btnLoad.post {
                    userAdapter.setData(users)
                }
            }.start()
        }


        rvUsers = findViewById(R.id.rv_users)
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter

        //клики внутри списка
        userAdapter.setListener(object : UserClickListener {
            override fun onItemClick(user: User) {
                Thread {
                    rvUsers.post {
                        Toast.makeText(
                            this@HelloActivity1,
                            "onItemClick() user=$user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.start()
            }

            override fun onMenuDeleteClick(user: User) {
                Thread {
                    db.deleteUserFromDB(user.login)
                    val users = db.getAllUsers()
                    val usersLog = users.joinToString(separator = ",\n")
                    Log.d(TAG, "Users:\n $usersLog")

                    rvUsers.post {
                        userAdapter.setData(users)
                        Toast.makeText(
                            this@HelloActivity1,
                            "onMenuDeleteClick() user=$user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.start()
            }

            override fun onMenuPassChangeClick(user: User) {
                Thread {
                    rvUsers.post {
                        db.PassChangeClick(user)
                    }
                }.start()
            }
        })

    }
}

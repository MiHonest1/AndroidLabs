package com.example.androidapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HelloActivity1 : AppCompatActivity() {

    companion object {
        private val TAG: String = HelloActivity1::class.java.simpleName
    }

    private lateinit var rvUsers: RecyclerView
    private lateinit var db: DatabaseHandler

    private lateinit var textViewTest: TextView
    private lateinit var buttonTest: Button
    private lateinit var btnLoad: Button
    private lateinit var handlerHelA: Handler
    private lateinit var button: Button
    private lateinit var userLogin: EditText
    private lateinit var userId: EditText
    private lateinit var userPass: EditText

    private val userAdapter = UserAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact1)

        db = DatabaseHandler(this, null)
        textViewTest = findViewById(R.id.textViewTest)
        buttonTest = findViewById(R.id.buttonTest)
        button = findViewById(R.id.button_reg)
        userLogin = findViewById(R.id.user_login)
        userId = findViewById(R.id.user_id)
        userPass = findViewById(R.id.user_pass)
        btnLoad= findViewById(R.id.btnLoad)

        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        // Создается экземпляр класса DatabaseThread и запускается в отдельном потоке
        val databaseThread = DatabaseThread(db)
        databaseThread.start()

        // Создается и инициализируется объект Handler для обработки сообщений в главном потоке
        handlerHelA = object : Handler(Looper.myLooper()!!) { //getMainLooper() возвращает экземпляр Looper, связанный с UI-потоком
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> {
                        textViewTest.text = msg.obj.toString()
                    }

                    2 -> {
                        val users = msg.obj as List<User>
                        val usersLog = users.joinToString(separator = ",\n")
                        Log.d(TAG, "Users:\n $usersLog")
                        userAdapter.setData(users)
                    }

                }
            }
        }

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
                Toast.makeText(this@HelloActivity1, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                //передать этого userа в другой класс DatabaseThread, везде разные loopы, обратно из DatabaseThread ничего не отправлять
                val user = User(id.toInt(), login, pass)

                val handlerDBThread = databaseThread.getHandler()
                val message = Message.obtain()
                message.what = 1
                message.obj = user
                handlerDBThread.sendMessage(message)

                Toast.makeText(this@HelloActivity1, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
            }
        }

        buttonTest.setOnClickListener {
            DatabaseThread(db).doSomething(handlerHelA)
        }

        //загрузка инфы из бд
        btnLoad.setOnClickListener {
            DatabaseThread(db).logUsers(handlerHelA)
        }


        rvUsers = findViewById(R.id.rv_users)
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter

        //клики внутри списка
        userAdapter.setListener(object : UserClickListener {
            override fun onItemClick(user: User) {
                rvUsers.post {
                    Toast.makeText(
                        this@HelloActivity1,
                        "onItemClick() user=$user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onMenuDeleteClick(user: User) {

                val handlerDBThread = databaseThread.getHandler()
                val message = Message.obtain()
                message.what = 3
                message.obj = user.login
                handlerDBThread.sendMessage(message)

                Toast.makeText(
                    this@HelloActivity1,
                    "onMenuDeleteClick() user=$user",
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onMenuPassChangeClick(user: User) {

                val handlerDBThread = databaseThread.getHandler()
                val message = Message.obtain()
                message.what = 2
                message.obj = user
                handlerDBThread.sendMessage(message)
            }
        })

    }
}

package com.example.labone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginField = findViewById<EditText>(R.id.field_login)
        val passwordField = findViewById<EditText>(R.id.field_password)
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        val checkboxAutoAuth = findViewById<CheckBox>(R.id.check_auto_login)

        val contentIntent = Intent(this, ContentActivity::class.java)
        val registrationIntent = Intent(this, RegistrationActivity::class.java)

        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val login = preferences.getString("login", null)
        val password = preferences.getString("password", null)
        val phoneRegex = "^[+]7\\d{10}\$".toRegex()

        // Если почему-то нет данных, то переходим обратно на регистрацию
        if (login == null || password == null) {
            Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
            startActivity(registrationIntent)
            return
        }

        // Если при регистрации был телефон, то меняет тип инпута
        if (phoneRegex.matches(login)) {
            loginField.hint = "+7"
            loginField.inputType = InputType.TYPE_CLASS_PHONE
        }

        confirmButton.setOnClickListener {
            if (loginField.text.toString() == login && passwordField.text.toString() == password) {
                preferences.edit().putBoolean("isAutoAuth", checkboxAutoAuth.isChecked).apply()
                startActivity(contentIntent)
            } else {
                Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
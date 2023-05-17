package com.example.labone

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)

        val contentIntent = Intent(this, ContentActivity::class.java)

        val authTypePhoneButton = findViewById<TextView>(R.id.auth_type_phone)
        val authTypeEmailButton = findViewById<TextView>(R.id.auth_type_email)

        val loginField = findViewById<EditText>(R.id.field_login)
        val passwordField = findViewById<EditText>(R.id.field_password)
        val passwordConfirmField = findViewById<EditText>(R.id.field_password_confirm)
        val confirmButton = findViewById<Button>(R.id.confirm_button)

        authTypePhoneButton.setOnClickListener{
            if (!authTypePhoneButton.typeface.isBold) {
                // Меняем вид кнопки логина по телефону
                authTypePhoneButton.typeface = Typeface.create(authTypePhoneButton.typeface, Typeface.BOLD)
                authTypePhoneButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primary))
                // Меняем вид кнопки логина по почте
                authTypeEmailButton.typeface = Typeface.create(authTypeEmailButton.typeface, Typeface.NORMAL)
                authTypeEmailButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.foreground_primary))
                // Меням поле логина
                loginField.hint = "+7"
                loginField.setText("")
                loginField.inputType = InputType.TYPE_CLASS_PHONE
            }
        }

        authTypeEmailButton.setOnClickListener{
            if (!authTypeEmailButton.typeface.isBold) {
                // Меняем вид кнопки логина по телефону
                authTypeEmailButton.typeface = Typeface.create(authTypeEmailButton.typeface, Typeface.BOLD)
                authTypeEmailButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primary))
                // Меняем вид кнопки логина по почте
                authTypePhoneButton.typeface = Typeface.create(authTypePhoneButton.typeface, Typeface.NORMAL)
                authTypePhoneButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.foreground_primary))
                // Меням поле логина
                loginField.hint = "Введите email"
                loginField.setText("")
                loginField.inputType =
                    (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            }
        }

        confirmButton.setOnClickListener {
            val emailRegex = "\\w+@\\w+[.]\\w+".toRegex()
            val phoneRegex = "^[+]7\\d{10}\$".toRegex()

            if (loginField.inputType == InputType.TYPE_CLASS_PHONE && !phoneRegex.matches(loginField.text)) {
                Toast.makeText(this, "Неправильный формат телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (loginField.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) && !emailRegex.matches(loginField.text)) {
                Toast.makeText(this, "Неправильный формат почты", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordField.text.length < 8) {
                Toast.makeText(this, "Пароль должен содержать минимум 8 символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordField.text.toString() != passwordConfirmField.text.toString()) {
                Toast.makeText(this, "Пароли должны совпадать", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            preferences.edit().putString("login", loginField.text.toString()).apply()
            preferences.edit().putString("password", passwordField.text.toString()).apply()
            startActivity(contentIntent)
        }
    }
}
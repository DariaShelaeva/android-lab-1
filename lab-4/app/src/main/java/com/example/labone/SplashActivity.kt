package com.example.labone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val login = preferences.getString("login", null)
        val password = preferences.getString("password", null)
        val isAutoAuth = preferences.getBoolean("isAutoAuth", false)

        val registrationIntent = Intent(this, RegistrationActivity::class.java)
        val loginIntent = Intent(this, LoginActivity::class.java)
        val contentIntent = Intent(this, ContentActivity::class.java)

        if (login == null || password == null) {
            // Переходим на экран регистрации
            startActivity(registrationIntent)
        }

        if (login != null && login.isNotBlank() && password != null && password.isNotBlank()) {
            if (isAutoAuth) {
                // Переходим на главный экран
                startActivity(contentIntent)
                return
            }
            // Переходим на экран логина
            startActivity(loginIntent)
        }
    }
}
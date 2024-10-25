package com.example.ems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val usuario = intent.getStringExtra("usuario")
        val senha = intent.getStringExtra("senha")

        val userInfoTextView: TextView = findViewById(R.id.userInfoTextView)
        userInfoTextView.text = "Usuário: $usuario\nSenha: $senha"
    }
}


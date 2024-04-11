package com.example.tymema_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var move: Button
    private lateinit var move2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        move = findViewById(R.id.login_btn)
        move.setOnClickListener {
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }

        move2 = findViewById(R.id.register_login_btn)
        move2.setOnClickListener {
            val intent = Intent(this@MainActivity, Registration::class.java)
            startActivity(intent)
        }
    }
}

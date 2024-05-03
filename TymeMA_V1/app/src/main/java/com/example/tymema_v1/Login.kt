package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Corrected layout file name

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        registerButton = findViewById(R.id.register_login_btn) // Corrected ID
        forgotPasswordButton = findViewById(R.id.forgot_password_btn)

        // Function to get registered credentials from SharedPreferences
        fun getRegisteredCredentials(context: Context): Pair<String?, String?> {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)
            return Pair(username, password)
        }

        loginButton.setOnClickListener {
            val enteredUsername = usernameInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            val (registeredUsername, registeredPassword) = getRegisteredCredentials(this)
            if (enteredUsername == registeredUsername && enteredPassword == registeredPassword) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // Navigate to next activity
                val intent = Intent(this@Login, Main_menu::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@Login, Registration::class.java)
            startActivity(intent)
        }

        // Forgot Password button click listener
        forgotPasswordButton.setOnClickListener {
            // Display a message for password reset
            Toast.makeText(this, "Password Reset", Toast.LENGTH_SHORT).show()
            // You can add further logic here for actual password reset functionality
        }
    }
}

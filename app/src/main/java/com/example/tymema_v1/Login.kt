package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
class Login : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        registerButton = findViewById(R.id.register_login_btn)
        forgotPasswordButton = findViewById(R.id.forgot_password_btn)

        loginButton.setOnClickListener {
            val enteredUsername = usernameInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            validateCredentials(enteredUsername, enteredPassword)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@Login, Registration::class.java)
            startActivity(intent)
        }

        forgotPasswordButton.setOnClickListener {
            Toast.makeText(this, "Password Reset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateCredentials(username: String, password: String) {
        val database = Firebase.database
        val usersRef = database.getReference("users")

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var validUser = false
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && user.username == username && user.password == password) {
                        validUser = true
                        break
                    }
                }
                if (validUser) {
                    Toast.makeText(this@Login, "Login successful", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Login successful")
                    val intent = Intent(this@Login, Main_menu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Invalid username or password")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Login, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

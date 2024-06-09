package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_registration)
    }

    //Use Firebase DB instead of sharepreferences
    fun saveCredentials(username: String, password: String) {
        val database = Firebase.database
        val usersRef = database.getReference("users")
        val userId = usersRef.push().key
        if (userId != null) {
            val user = User(username, password)
            usersRef.child(userId).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                    Log.d("Registration", "User registered successfully")
                } else {
                    Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show()
                    Log.e("Registration", "Failed to register user: ${task.exception?.message}")
                }
            }
        }
    }

    fun onRegisterButtonClick(view: View) {
        val nameInput = findViewById<EditText>(R.id.name_input)
        val surnameInput = findViewById<EditText>(R.id.surname_input)
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        val name = nameInput.text.toString()
        val surname = surnameInput.text.toString()
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        saveCredentials(username, password)

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}

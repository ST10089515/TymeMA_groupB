package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_registration)
    }

    fun saveCredentials(context: Context, username: String, password: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
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

        saveCredentials(this, username, password)

        // Now you can use name, surname, username, and password variables to perform further actions
        // such as saving the information or validating credentials
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}

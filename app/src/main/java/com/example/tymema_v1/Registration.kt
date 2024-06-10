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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_registration)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    //Use Firebase DB instead of sharepreferences
    private fun registerUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Registration", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "Registration successful.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, Login::class.java) //Proceed to Login upon successful registration
                    startActivity(intent)
                } else {
                    // If registration fails, display a message to the user.
                    Log.w("Registration", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Registration failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun onRegisterButtonClick(view: View) {
        val emailInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        registerUserWithEmailAndPassword(email, password)
    }
}

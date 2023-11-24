package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register = findViewById<TextView>(R.id.new_register)

        register.setOnClickListener {
            val changePage = Intent(this, RegisterActivity::class.java)
            startActivity(changePage)
            finish()
        }

        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            val emailValue = findViewById<EditText>(R.id.input_email).text.toString()
            val passwordValue = findViewById<EditText>(R.id.input_password).text.toString()

            if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
                processLogin(emailValue, passwordValue)
            } else {
                Toast.makeText(this, "Silahkan Isi Semua Field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processLogin(emailValue: String, passwordValue: String) {
        firebaseAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Selesai activity setelah berhasil login
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}

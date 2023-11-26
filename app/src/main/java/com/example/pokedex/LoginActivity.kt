package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.database.AppDatabase
import com.example.pokedex.database.entitas.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        db = AppDatabase.getInstance(this)

        val register = findViewById<TextView>(R.id.tv_route_register)

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
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                user?.let {
                    val username = user.displayName ?: ""
                    val userEmail = user.email ?: ""
                    val userId = user.uid ?: ""

                    // Pastikan data user tidak null sebelum disimpan ke dalam Room Database
                    if (username.isNotEmpty() && userEmail.isNotEmpty() && userId.isNotEmpty()) {
                        val newUser = User().apply {
                            this.username = username
                            this.email = userEmail
                            this.id = userId
                        }

                        // Masukkan data user ke dalam Room Database
                        saveUserToDatabase(newUser)
                    } else {
                        // Data user tidak lengkap, berikan pesan kesalahan
                        Toast.makeText(this, "Data pengguna tidak lengkap", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { error ->
                // Tangani kegagalan proses login
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserToDatabase(user: User) {
        // Gunakan coroutine untuk menjalankan operasi database di luar thread utama
        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().register(user)
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        // Pastikan navigasi dilakukan di thread UI
        runOnUiThread {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Selesai activity setelah berhasil login
        }
    }

}

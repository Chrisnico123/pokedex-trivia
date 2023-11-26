package com.example.pokedex

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlin.text.isNotEmpty as isNotEmpty1

class RegisterActivity : AppCompatActivity() {

    private var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var loginAcc = findViewById<TextView>(R.id.tv_route_login)

        loginAcc.setOnClickListener {
            val changePage = Intent(this, LoginActivity::class.java)
            startActivity(changePage)
            finish()
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu")

        var usernameEditText = findViewById<EditText>(R.id.input_username)
        var emailEditText = findViewById<EditText>(R.id.input_email)
        var passwordEditText = findViewById<EditText>(R.id.input_password)
        var repasswordEditText = findViewById<EditText>(R.id.input_repassword)

        var registerButton = findViewById<Button>(R.id.btn_register)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val repassword = repasswordEditText.text.toString()

            if (username.isNotEmpty1() && email.isNotEmpty1() && password.isNotEmpty1() && repassword.isNotEmpty1()) {
                processRegister(username, email, password, repassword)
            } else {
                Toast.makeText(this, "Silahkan Isi Semua Field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister(username: String, email: String, password: String, repassword: String) {
        progressDialog.show()
        if (password == repassword) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userUpdate = userProfileChangeRequest {
                            displayName = username
                        }

                        val user = firebaseAuth.currentUser
                        user!!.updateProfile(userUpdate)
                            .addOnCompleteListener {
                                progressDialog.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { error2 ->
                                progressDialog.dismiss()
                                Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this, task.exception?.localizedMessage ?: "Registrasi gagal", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { error ->
                    progressDialog.dismiss()
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        } else {
            progressDialog.dismiss()
            Toast.makeText(this, "Password dan Repassword tidak sama", Toast.LENGTH_SHORT).show()
        }
    }
}

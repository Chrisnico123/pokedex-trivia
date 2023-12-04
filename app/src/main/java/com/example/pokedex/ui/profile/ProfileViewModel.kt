package com.example.pokedex.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    // Function untuk mendapatkan data username dari Firebase
    fun getUsername(): String {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.displayName ?: "Username not found"
    }

    // Function untuk mendapatkan data email dari Firebase
    fun getEmail(): String {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.email ?: "Email not found"
    }


    // Function untuk logout user dari Firebase
    fun logout() {
        firebaseAuth.signOut()
    }
}

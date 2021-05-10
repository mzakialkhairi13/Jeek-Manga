package com.mzakialkhairi.manga.utils

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseUserLiveData : LiveData<FirebaseUser?>() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val authStateListener = FirebaseAuth.AuthStateListener {
            firebaseAuth -> value = firebaseAuth.currentUser
    }
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}
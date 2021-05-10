package com.mzakialkhairi.manga.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ActivitySplashScreenBinding
import com.mzakialkhairi.manga.ui.auth.AuthenticationActivity
import com.mzakialkhairi.manga.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var handler: Handler
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        auth = Firebase.auth
        val currentUser = auth.currentUser


        handler = Handler()
        handler.postDelayed({
            if  (currentUser == null){
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        },3000)

    }
}
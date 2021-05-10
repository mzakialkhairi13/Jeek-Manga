package com.mzakialkhairi.manga.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ActivityAuthenticationBinding
import com.mzakialkhairi.manga.model.User
import com.mzakialkhairi.manga.model.UserRegister
import com.mzakialkhairi.manga.ui.main.MainActivity

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthenticationBinding
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_authentication)
        binding.regReg.setOnClickListener{
            val nama = binding.regNama.text.toString().trim()
            val email = binding.regEmail.text.toString().trim()
            val password = binding.regPassword.text.toString().trim()
            val data = UserRegister(nama = nama,email = email,password = password)
            registerUser(data)
        }
    }


    fun registerUser (data : UserRegister) {
        if (data.email.isNotEmpty() && data.password.isNotEmpty() && data.nama.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(data.email, data.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveToDatabase(data)
                    } else {
                        Toast.makeText(
                            this,
                            "Maaf kita coba lain kali ya" + it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Maaf kita coba lain kali ya: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    private fun saveToDatabase(data: UserRegister){
        val uid = mAuth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(
            name = data.nama,
            email = data.email,
            profilePicture = "https://i.pinimg.com/originals/9c/9c/17/9c9c1763654c037f4535007304ee502f.png",
            role = 1
        )
        ref.setValue(user)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sepertinya terjadi kesalahan:  ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
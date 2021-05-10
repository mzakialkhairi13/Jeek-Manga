package com.mzakialkhairi.manga.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.SectionsPagerAdapter
import com.mzakialkhairi.manga.databinding.ActivityMainBinding
import com.mzakialkhairi.manga.model.User
import com.mzakialkhairi.manga.utils.show
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        updateUI()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }


    private fun updateUI(){
        binding.apply {
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            val usersReference = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUser!!.uid)
            usersReference.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val user: User? = p0.getValue(User::class.java)
                        toolbar.title.text = user!!.name
                        Glide.with(this@MainActivity).load(user.profilePicture).into(profile_picture)
                        if (user.role == 2){
                            verfied.show()
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })

        }
    }
}
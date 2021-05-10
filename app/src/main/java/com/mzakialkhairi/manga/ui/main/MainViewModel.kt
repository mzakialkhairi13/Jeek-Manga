package com.mzakialkhairi.manga.ui.main

import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.utils.FirebaseUserLiveData


class MainViewModel : ViewModel() {
    val userAuth = FirebaseUserLiveData()
}
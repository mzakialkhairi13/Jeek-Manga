package com.mzakialkhairi.manga.listeners

import android.view.View
import com.mzakialkhairi.manga.model.Komik

interface OnKomikClicked {
    fun onKomikIsCLicked(view : View , komik: Komik)
}
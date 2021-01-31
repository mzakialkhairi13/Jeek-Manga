package com.mzakialkhairi.manga.utils.interfaces

import android.view.View
import com.mzakialkhairi.manga.model.Komik

interface OnKomikClicked {
    fun onKomikIsCLicked(view : View , komik: Komik)
}
package com.mzakialkhairi.manga.listeners

import android.view.View
import com.mzakialkhairi.manga.model.Manga

interface OnMangaClicked {
    fun mangaClicked(view: View, manga: Manga)
}
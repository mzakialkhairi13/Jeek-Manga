package com.mzakialkhairi.manga.listeners

import android.view.View
import com.mzakialkhairi.manga.model.Chapter

interface OnChapterClicked {
    fun chapterClicked(view : View , chapter: Chapter)
}
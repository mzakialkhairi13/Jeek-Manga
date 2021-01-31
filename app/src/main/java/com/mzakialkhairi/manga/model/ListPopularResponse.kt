package com.mzakialkhairi.manga.model

data class ListPopularResponse(
    val manga_list: List<Komik>,
    val message: String,
    val status: Boolean
)
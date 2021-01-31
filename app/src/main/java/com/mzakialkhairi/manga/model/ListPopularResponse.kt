package com.mzakialkhairi.manga.model

data class ListPopularResponse(
    val manga_list: List<Manga>,
    val message: String,
    val status: Boolean
)
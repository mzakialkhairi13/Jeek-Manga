package com.mzakialkhairi.manga.model

data class DetailKomikResponse(
    val author: String,
    val chapter: List<Chapter>,
    val genre_list: List<Genre>,
    val manga_endpoint: String,
    val status: String,
    val synopsis: String,
    val thumb: String,
    val title: String,
    val type: String
)
package com.mzakialkhairi.manga.model

data class Manga(
    val endpoint: String,
    val thumb: String,
    val title: String,
    val type: String,
    val upload_on: String
)
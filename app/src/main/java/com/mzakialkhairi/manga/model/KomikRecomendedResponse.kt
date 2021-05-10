package com.mzakialkhairi.manga.model

data class KomikRecomendedResponse(
    val manga_list: List<Manga>?,
    val message: String?,
    val status: Boolean?
)
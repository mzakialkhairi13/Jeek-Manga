package com.mzakialkhairi.manga.model

data class Komik(
    val endpoint: String,
    val thumb: String,
    val title: String,
    val type: String,
    val upload_on: String ?= null,
    val updload_on: String ?= null,
    val updated_on: String ?= null
)
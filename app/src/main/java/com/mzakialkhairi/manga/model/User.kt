package com.mzakialkhairi.manga.model

data class User(
    val name : String ? = null,
    val profilePicture : String ? = null,
    val email : String ? = null,
    val role : Int ? = null
)
package com.mzakialkhairi.manga.model

sealed class States {
    data class error(var error :String) : States()
    data class showToast(var message : String) : States()
    data class isLoading(var state : Boolean? = false) : States()
}
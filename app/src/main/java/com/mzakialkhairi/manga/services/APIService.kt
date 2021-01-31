package com.mzakialkhairi.manga.services

import com.mzakialkhairi.manga.model.ListPopularResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("popular/1")
    fun getPopularList()  : Call<ListPopularResponse>


}
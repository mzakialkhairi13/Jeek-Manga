package com.mzakialkhairi.manga.services

import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.model.ListPopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("manga/popular/{page}")
    fun getPopularList(@Path("page") id : Int)  : Call<ListPopularResponse>

    @GET("manga/detail/{endpoint}")
    fun getDetailKomik(@Path ("endpoint") endpoint : String ) : Call<DetailKomikResponse>

}
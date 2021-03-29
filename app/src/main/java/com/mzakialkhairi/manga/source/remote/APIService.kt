package com.mzakialkhairi.manga.source.remote

import com.mzakialkhairi.manga.model.DetailChapterResponse
import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.model.ListPopularResponse
import com.mzakialkhairi.manga.model.SearchMangaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("manga/popular/{page}")
    fun getPopularList(@Path("page") id : Int)  : Call<ListPopularResponse>

    @GET("manga/detail/{endpoint}")
    fun getDetailKomik(@Path ("endpoint") endpoint : String ) : Call<DetailKomikResponse>

    @GET("chapter/{endpoint}")
    fun getChapterDetailKomik(@Path ("endpoint") endpoint : String ) : Call<DetailChapterResponse>

    @GET("search/{keyword}")
    fun getListSearch(@Path ("keyword") keyword : String ) : Call<SearchMangaResponse>

}
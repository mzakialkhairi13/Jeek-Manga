package com.mzakialkhairi.manga.services

import com.mzakialkhairi.manga.utils.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private var opt = OkHttpClient.Builder().apply {
        writeTimeout(30,TimeUnit.SECONDS)
        readTimeout(30,TimeUnit.SECONDS)
        connectTimeout(30,TimeUnit.SECONDS)
    }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constant.URL_BASE)
        .client(opt)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val INSTANCE_API  : APIService by lazy{
        retrofit.create(APIService::class.java)
    }

}
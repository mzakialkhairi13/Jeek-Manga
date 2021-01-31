package com.mzakialkhairi.manga.ui.detail_komik

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.services.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailKomikViewModel : ViewModel(){

    private val _dataKomik = MutableLiveData<DetailKomikResponse>()
    val dataKomik : LiveData<DetailKomikResponse>
    get() = _dataKomik

    private val _listGenre = MutableLiveData<List<String>>()
    val listGenre : LiveData<List<String>>
        get() = _listGenre

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)


    fun getDataKomik(endpoint : String){
        uiScope.apply {
            RetrofitInstance.INSTANCE_API.getDetailKomik(endpoint).enqueue(object : Callback<DetailKomikResponse>{
                override fun onResponse(
                    call: Call<DetailKomikResponse>,
                    response: Response<DetailKomikResponse>
                ) {
                    _dataKomik.value = response.body()
                }

                override fun onFailure(call: Call<DetailKomikResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

}
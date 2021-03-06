package com.mzakialkhairi.manga.ui.detail_komik

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.source.remote.RetrofitInstance
import com.mzakialkhairi.manga.utils.SingleLiveEvent
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

    val states : SingleLiveEvent<States> = SingleLiveEvent()

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)


    fun getDataKomik(endpoint : String){
        try {
            uiScope.apply {
                states.postValue(States.isLoading())
                RetrofitInstance.INSTANCE_API.getDetailKomik(endpoint).enqueue(object : Callback<DetailKomikResponse>{
                    override fun onResponse(
                        call: Call<DetailKomikResponse>,
                        response: Response<DetailKomikResponse>
                    ) {
                        states.postValue(States.isLoading(false))
                        _dataKomik.value = response.body()
                    }

                    override fun onFailure(call: Call<DetailKomikResponse>, t: Throwable) {
                        states.postValue(States.isLoading(false))
                        states.postValue(States.error(t.toString()))
                    }
                })
            }
        }
        catch (e: Exception){
            states.postValue(States.error(e.toString()))
        }
    }

    fun getState() = states

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
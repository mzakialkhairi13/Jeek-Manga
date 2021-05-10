package com.mzakialkhairi.manga.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.adapter.ListRecommendedAdapter
import com.mzakialkhairi.manga.model.*
import com.mzakialkhairi.manga.source.remote.RetrofitInstance
import com.mzakialkhairi.manga.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listRecommended = MutableLiveData<ArrayList<Manga>>()
    val listRecommended : LiveData<ArrayList<Manga>>
        get() = _listRecommended

    private var state : SingleLiveEvent<States> = SingleLiveEvent()

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun getRecommended(){
        uiScope.launch {
            try {
                state.value = States.isLoading(true)
                RetrofitInstance.INSTANCE_API.getRecomended().enqueue(object :
                    Callback<KomikRecomendedResponse> {
                    override fun onResponse(
                        call: Call<KomikRecomendedResponse>,
                        response: Response<KomikRecomendedResponse>
                    ) {
                        val responseBody = response.body()!!.manga_list
                        if (responseBody!!.isNotEmpty()){
                            _listRecommended.value = responseBody as ArrayList<Manga>
                            state.value = States.isLoading(false)
                        }

                    }

                    override fun onFailure(call: Call<KomikRecomendedResponse>, t: Throwable) {
                        state.value = States.error(t.message.toString())
                        state.value = States.isLoading(false)
                    }

                })
            }
            catch (t : Throwable){
                state.value = States.error(t.message.toString())
                state.value = States.isLoading(false)
            }
        }
    }

    fun getStates () = state

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

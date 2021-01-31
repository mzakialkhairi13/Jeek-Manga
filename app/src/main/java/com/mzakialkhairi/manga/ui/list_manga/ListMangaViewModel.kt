package com.mzakialkhairi.manga.ui.list_manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.model.ListPopularResponse
import com.mzakialkhairi.manga.model.Manga
import com.mzakialkhairi.manga.services.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMangaViewModel : ViewModel() {

    private val _listPopular = MutableLiveData<ArrayList<Manga>>()
    val listPopular : LiveData<ArrayList<Manga>>
    get() = _listPopular

    private val _state = MutableLiveData<String>()
    val state : LiveData<String>
    get() = _state

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        getListPopular()
    }

    private fun getListPopular(){
        uiScope.launch {
            try {
                RetrofitInstance.INSTANCE_API.getPopularList().enqueue(object :
                    Callback<ListPopularResponse> {
                    override fun onResponse(
                        call: Call<ListPopularResponse>,
                        response: Response<ListPopularResponse>
                    ) {
                        val responseBody = response.body()!!.manga_list
                        if (responseBody.isNotEmpty()){
                            _listPopular.value = responseBody as ArrayList<Manga>
                        }
                        else {
                            _state.value = "List Kosong"
                        }
                    }

                    override fun onFailure(call: Call<ListPopularResponse>, t: Throwable) {
                        _state.value = t.message
                    }

                })
            }
            catch (t : Throwable){
                _state.value = t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

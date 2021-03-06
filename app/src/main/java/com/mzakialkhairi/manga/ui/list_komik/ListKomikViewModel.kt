package com.mzakialkhairi.manga.ui.list_komik

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.model.ListPopularResponse
import com.mzakialkhairi.manga.model.Komik
import com.mzakialkhairi.manga.model.SearchMangaResponse
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.source.remote.RetrofitInstance
import com.mzakialkhairi.manga.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListKomikViewModel : ViewModel() {

    var page : Int = 1

    private val _listPopular = MutableLiveData<ArrayList<Komik>>()
    val listPopular : LiveData<ArrayList<Komik>>
    get() = _listPopular

    private val _searchKomik= MutableLiveData<SearchMangaResponse>()
    val searchKomik : LiveData<SearchMangaResponse>
        get() = _searchKomik

    private var state : SingleLiveEvent<States> = SingleLiveEvent()

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun getListPopular(page : Int){
        uiScope.launch {
            try {
                state.value = States.isLoading(true)
                RetrofitInstance.INSTANCE_API.getPopularList(page).enqueue(object :
                    Callback<ListPopularResponse> {
                    override fun onResponse(
                        call: Call<ListPopularResponse>,
                        response: Response<ListPopularResponse>
                    ) {
                        val responseBody = response.body()!!.manga_list
                        if (responseBody.isNotEmpty()){
                            _listPopular.value = responseBody as ArrayList<Komik>
                            state.value = States.isLoading(false)
                        }

                    }

                    override fun onFailure(call: Call<ListPopularResponse>, t: Throwable) {
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

    fun getSearchKomik(keyword : String){
        uiScope.launch {
            try {
                state.value = States.isLoading(true)
                RetrofitInstance.INSTANCE_API.getListSearch(keyword).enqueue(object :
                    Callback<SearchMangaResponse> {
                    override fun onResponse(
                        call: Call<SearchMangaResponse>,
                        response: Response<SearchMangaResponse>
                    ) {
                        val responseBody = response.body()
                        if (response.isSuccessful){
                            _searchKomik.value = responseBody
                            state.value = States.isLoading(false)
                        }
                    }
                    override fun onFailure(call: Call<SearchMangaResponse>, t: Throwable) {
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

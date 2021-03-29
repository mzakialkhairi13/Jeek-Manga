package com.mzakialkhairi.manga.ui.detail_chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.manga.model.DetailChapterResponse
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

class DetailChapterViewModel : ViewModel() {

    val states : SingleLiveEvent<States> = SingleLiveEvent()

    private val _dataChapter = MutableLiveData<DetailChapterResponse>()
    val dataChapter: LiveData<DetailChapterResponse>
        get() = _dataChapter

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)


    fun getChapterImage(endpoint: String) {
        try {
            states.postValue(States.isLoading(true))
            uiScope.apply {
                RetrofitInstance.INSTANCE_API.getChapterDetailKomik(endpoint).enqueue(object :
                    Callback<DetailChapterResponse> {
                    override fun onResponse(
                        call: Call<DetailChapterResponse>,
                        response: Response<DetailChapterResponse>
                    ) {
                        states.postValue(States.isLoading(false))
                        _dataChapter.value = response.body()
                    }

                    override fun onFailure(call: Call<DetailChapterResponse>, t: Throwable) {
                        states.postValue(States.isLoading(false))
                        states.postValue(States.error(t.toString()))
                    }

                })

            }
        }
        catch (e : Exception){
            states.postValue(States.isLoading(false))
            states.postValue(States.error(e.toString()))
        }

    }

    fun getState() = states

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
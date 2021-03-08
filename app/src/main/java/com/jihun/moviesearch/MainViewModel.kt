package com.jihun.moviesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel: ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    val mainLiveData: MutableLiveData<MainDataSet> = MutableLiveData()

    fun requestMovieData(keyword: String?) {

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    data class MainDataSet(
        var data: Any?,
        var response: ResponseType = ResponseType.FAIL
    )

    enum class ResponseType {
        UPDATE,
        FAIL
    }
}
package com.jihun.moviesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jihun.moviesearch.api.RetrofitClient
import com.jihun.moviesearch.data.*
import com.jihun.moviesearch.data.ResponseType.*
import com.jihun.moviesearch.data.ViewType.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    val mainLiveData: MutableLiveData<MainLiveDataResponse> = MutableLiveData()

    fun requestMovieData(keyword: String?) {
        compositeDisposable.add(
            RetrofitClient
                .getInstance()
                .getService()
                .getTrendingInfo(keyword)
                .subscribeOn(Schedulers.io())
                .map(::createViewEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mainLiveData.value = MainLiveDataResponse(
                            response = UPDATE,
                            data = it
                        )
                    }, {
                        mainLiveData.value = MainLiveDataResponse(
                            response = FAIL
                        )
                    }
                )
        )
    }

    private fun createViewEntity(data: ApiMovieData): MutableList<MainDataSet> {
        val mainData: MutableList<MainDataSet> = mutableListOf()
        data.getApiFirstData()?.let {
            it.Result?.forEach { apiItem ->
                mainData.add(
                    MainDataSet(
                        viewType = MOVIE_ITEM_VIEW_TYPE,
                        data = MovieData(
                            uniqueId = apiItem.DOCID,
                            imgUrl = apiItem.getPosterImg(),
                            title = apiItem.title,
                            directorNm = apiItem.directors?.getDirector(),
                            releaseDate = apiItem.getDate()
                        )
                    )
                )
            }
        } ?: run {
            mainData.add(
                MainDataSet(
                    viewType = EMPTY_VIEW_TYPE
                )
            )
        }
        return mainData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
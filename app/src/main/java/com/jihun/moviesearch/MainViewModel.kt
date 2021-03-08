package com.jihun.moviesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jihun.moviesearch.api.RetrofitClient
import com.jihun.moviesearch.data.ApiMovieData
import com.jihun.moviesearch.data.MovieData
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
                            response = ResponseType.UPDATE,
                            data = it
                        )
                    }, {
                        mainLiveData.value = MainLiveDataResponse(
                            response = ResponseType.FAIL
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
                        viewType = ViewType.MOVIE_ITEM_VIEW_TYPE,
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
                    viewType = ViewType.EMPTY_VIEW_TYPE
                )
            )
        }
        return mainData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    data class MainLiveDataResponse(
        var response: ResponseType = ResponseType.FAIL,
        var data: MutableList<MainDataSet>? = null,
    )

    data class MainDataSet(
        var data: Any? = null,
        var viewType: ViewType = ViewType.EMPTY_VIEW_TYPE
    )

    enum class ResponseType {
        UPDATE,
        FAIL
    }

    enum class ViewType {
        EMPTY_VIEW_TYPE,
        MOVIE_ITEM_VIEW_TYPE
    }
}
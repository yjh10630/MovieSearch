package com.jihun.moviesearch.data

data class MainLiveDataResponse(
    var response: ResponseType = ResponseType.FAIL,
    var data: MutableList<MainDataSet>? = null,
)

enum class ResponseType {
    UPDATE,
    FAIL
}


data class MainDataSet(
    var data: Any? = null,
    var viewType: ViewType = ViewType.EMPTY_VIEW_TYPE
)

enum class ViewType {
    EMPTY_VIEW_TYPE,
    MOVIE_ITEM_VIEW_TYPE
}
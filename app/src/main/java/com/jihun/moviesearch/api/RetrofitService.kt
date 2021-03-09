package com.jihun.moviesearch.api

import com.jihun.moviesearch.data.ApiMovieData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&listCount=100")
    fun getTrendingInfo(@Query("keyword") keyword: String?): Observable<ApiMovieData>
}
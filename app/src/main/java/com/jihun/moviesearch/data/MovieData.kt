package com.jihun.moviesearch.data

import com.google.gson.annotations.SerializedName
import com.jihun.moviesearch.utils.getDateFormat

/**
 * Model
 */
data class MovieData (
    var uniqueId: String? = null,
    var imgUrl: String? = null,
    var title: String? = null,
    var directorNm: String? = null,
    var releaseDate: String? = null
)

/**
 * Api Response Data Class
 */
data class ApiMovieData (
    @SerializedName("Data") val data: MutableList<ApiData>? = null   //?? 이걸 왜 리스트로 주니 ? ..
) {
    fun getApiFirstData(): ApiData? = data?.getOrNull(0)
}

data class ApiData (
    @SerializedName("TotalCount") val totalCount: Int,
    @SerializedName("Count") val Count: Int,
    @SerializedName("Result") val Result: MutableList<Result>? = null
)

data class Result (
    @SerializedName("DOCID") val DOCID: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("directors") val directors: Directors? = null,
    @SerializedName("repRlsDate") val repRlsDate: String? = null,
    @SerializedName("posters") val posters: String? = null
) {
    fun getPosterImg(): String? = posters?.split("|")?.getOrNull(0)
    fun getDate(): String? = getDateFormat(repRlsDate ?: "").toString()
}

data class Directors (
    @SerializedName("director") val director: MutableList<Director>? = null
) {
    fun getDirector(): String {
        var name = ""
        director?.forEachIndexed { index, data ->
            name += "${data.directorNm} "
        }
        return name
    }
}

data class Director (
    @SerializedName("directorNm") val directorNm: String? = null,
    @SerializedName("directorEnNm") val directorEnNm: String? = null
)

data class Ratings (
    @SerializedName("rating") val rating: MutableList<Rating>? = null
) {
    fun getReleaseDate(): String? = rating?.getOrNull(0)?.releaseDate
}

data class Rating (
    @SerializedName("releaseDate") val releaseDate: String? = null
)


package com.jihun.moviesearch.utils

import android.content.res.Resources
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

inline val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Float.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun getScreenWidthToPx(): Int = Resources.getSystem().displayMetrics.widthPixels

fun getDateFormat(input: String): String? {
    var output = ""
    try {
        val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(input)
        output = SimpleDateFormat("yyyy년MM월dd일", Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    } finally {
        return output
    }
}
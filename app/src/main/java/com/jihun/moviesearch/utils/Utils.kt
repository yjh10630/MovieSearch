package com.jihun.moviesearch.utils

import android.content.res.Resources

inline val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Float.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun getScreenWidthToPx(): Int = Resources.getSystem().displayMetrics.widthPixels


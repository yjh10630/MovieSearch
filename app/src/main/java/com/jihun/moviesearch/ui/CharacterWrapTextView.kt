package com.jihun.moviesearch.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CharacterWrapTextView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(getCharWrapStr(text.toString()), type)
    }

    private fun getCharWrapStr(str: String): String {
        return str.replace(".(?!$)".toRegex(), "$0\u200b")
    }
}
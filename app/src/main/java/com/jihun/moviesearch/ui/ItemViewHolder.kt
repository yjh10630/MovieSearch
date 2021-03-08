package com.jihun.moviesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.jihun.moviesearch.R
import com.jihun.moviesearch.data.MovieData
import com.jihun.moviesearch.databinding.ViewMovieItemBinding
import com.jihun.moviesearch.utils.getScreenWidthToPx

class ItemViewHolder(parent: ViewGroup): BaseViewHolder<ViewMovieItemBinding>(
    ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun onBindView(data: Any?): Boolean {
        (data as? MovieData)?.let {
            initView(it)
            return true
        } ?: run {
            return false
        }
    }

    private fun initView(data: MovieData) {
        with (binding) {
            //1:1.5 비율 계산.. ( 해상도 고려 )
            posterImg.apply {
                val lp = layoutParams
                lp?.let {
                    lp.height = ((getScreenWidthToPx() / 2) * 1.5).toInt()
                    layoutParams = lp
                }
            }

            Glide.with(itemView.context)
                .load(data.imgUrl)
                .placeholder(ContextCompat.getDrawable(itemView.context, R.color.black))
                .into(posterImg)

            tvTitle.text = data.title ?: ""
            tvDirectorNm.text = data.directorNm ?: ""
            tvReleaseDate.text = data.releaseDate ?: ""
        }
    }
}
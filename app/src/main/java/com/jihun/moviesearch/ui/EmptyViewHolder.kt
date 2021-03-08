package com.jihun.moviesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jihun.moviesearch.databinding.ViewEmptyItemBinding

class EmptyViewHolder(parent: ViewGroup): BaseViewHolder<ViewEmptyItemBinding>(
    ViewEmptyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun onBindView(data: Any?): Boolean = true
}
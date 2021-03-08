package com.jihun.moviesearch.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<B: ViewBinding>(open val binding: B): RecyclerView.ViewHolder(binding.root) {
    abstract fun onBindView(data: Any?): Boolean
}
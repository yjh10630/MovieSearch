package com.jihun.moviesearch.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jihun.moviesearch.data.MovieData
import com.jihun.moviesearch.utils.notifyRecyclerView

class MainListAdapter: RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {

    var items: MutableList<MovieData>? = null
        set(value) {
            value?.let {
                notifyRecyclerView(
                    oldList = field,
                    newList = it,
                    itemCompare = {o, n -> o?.uniqueId == n?.uniqueId},
                    contentCompare = {o, n -> o == n}
                )
                field?.let {
                    it.clear()
                    it.addAll(value)
                } ?: run {
                    field = value
                }
            } ?: run {
                return
            }
        }

    override fun getItemCount(): Int = items?.size ?: 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> =
        ItemViewHolder(parent) as BaseViewHolder<ViewBinding>

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        holder.onBindView(items?.getOrNull(position))
    }
}
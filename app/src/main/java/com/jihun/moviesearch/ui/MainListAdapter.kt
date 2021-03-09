package com.jihun.moviesearch.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jihun.moviesearch.data.MainDataSet
import com.jihun.moviesearch.data.MovieData
import com.jihun.moviesearch.data.ViewType.*
import com.jihun.moviesearch.utils.notifyRecyclerView

class MainListAdapter: RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {

    var items: MutableList<MainDataSet>? = null
        set(value) {
            value?.let {
                notifyRecyclerView(
                    oldList = field,
                    newList = it,
                    itemCompare = {o, n ->
                        if (n?.viewType == MOVIE_ITEM_VIEW_TYPE) {
                            (n?.data as? MovieData)?.uniqueId == (o?.data as? MovieData)?.uniqueId
                        } else {
                            n?.viewType == o?.viewType
                        }
                    },
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

    override fun getItemViewType(position: Int): Int = items?.getOrNull(position)?.viewType?.ordinal ?: 0
    override fun getItemCount(): Int = items?.size ?: 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        val holder = when(values().getOrNull(viewType)) {
            MOVIE_ITEM_VIEW_TYPE -> ItemViewHolder(parent)
            else -> EmptyViewHolder(parent)
        }
        return holder as BaseViewHolder<ViewBinding>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        holder.onBindView(items?.getOrNull(position)?.data)
    }
}
package com.jihun.moviesearch

import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jihun.moviesearch.MainViewModel.*
import com.jihun.moviesearch.MainViewModel.ResponseType.FAIL
import com.jihun.moviesearch.MainViewModel.ResponseType.UPDATE
import com.jihun.moviesearch.MainViewModel.ViewType.*
import com.jihun.moviesearch.databinding.ActivityMainBinding
import com.jihun.moviesearch.ui.MainListAdapter
import com.jihun.moviesearch.utils.toPx

class MainActivity: BaseActivity<ActivityMainBinding>() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mInputMethodManager: InputMethodManager

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreatedBindingWithSetContentView(savedInstanceState: Bundle?) {
        initEditText()
        initRecyclerView()
        initViewModel()
    }

    private fun initEditText() {
        binding.userInputEditText.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mainViewModel.requestMovieData(binding.userInputEditText.text.toString())  // Initial keyword
                hideKeyboard()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun initRecyclerView() {
        binding.mainList.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2).also {
                it.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val emptyView = (adapter as? MainListAdapter)?.items?.firstOrNull { it.viewType == EMPTY_VIEW_TYPE }
                        return if (emptyView != null) 2
                        else 1
                    }
                }
            }
            adapter = MainListAdapter()
            if (itemDecorationCount == 0)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)

                        var position: Int = parent.getChildAdapterPosition(view) // item position
                        if (position == RecyclerView.NO_POSITION) position = parent.getChildLayoutPosition(view)
                        val spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 2
                        val spacing = 10.toPx

                        val column: Int = position % 2 // item column

                        outRect.left = spacing - column * spacing / spanCount
                        outRect.right = (column + 1) * spacing / spanCount

                        if (position < spanCount) { // top edge
                            outRect.top = spacing
                        }
                        outRect.bottom = spacing     // item bottom
                    }
                })

            setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }
    }

    private fun initViewModel() {
        mainViewModel.apply {
            requestMovieData("아이언맨")  // Initial keyword
            mainLiveData.observe(this@MainActivity, Observer {
                when (it.response) {
                    UPDATE -> {
                        (binding.mainList.adapter as? MainListAdapter)?.items = it.data
                    }
                    FAIL -> {
                        //TODO Fail Alert
                        Toast.makeText(this@MainActivity, "실피요...", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            if (!::mInputMethodManager.isInitialized) {
                mInputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            }
            mInputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }
}
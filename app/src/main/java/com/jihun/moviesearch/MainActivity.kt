package com.jihun.moviesearch

import android.os.Bundle
import com.jihun.moviesearch.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreatedBindingWithSetContentView(savedInstanceState: Bundle?) {

    }
}
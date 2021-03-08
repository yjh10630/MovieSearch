package com.jihun.moviesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B: ViewBinding>: AppCompatActivity() {

    protected lateinit var binding: B

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        onCreatedBindingWithSetContentView(savedInstanceState)
    }
    abstract fun getViewBinding(): B
    abstract fun onCreatedBindingWithSetContentView(savedInstanceState: Bundle?)
}
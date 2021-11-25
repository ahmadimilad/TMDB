package com.tmdb.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.tmdb.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater) -> VB

    protected val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindLayout.invoke(layoutInflater)
        setTheme(R.style.Theme_TMDB)
        setContentView(requireNotNull(_binding).root)
        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
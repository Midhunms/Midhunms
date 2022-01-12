package com.midhun.technical.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


abstract class ScopedActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        job = Job()
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {

        super.setContentView(layoutResID)
        bindUI()
    }
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected open fun bindUI(): Job = launch { }

}
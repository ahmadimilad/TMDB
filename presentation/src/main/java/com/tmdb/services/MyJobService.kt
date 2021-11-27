package com.tmdb.services

import android.app.job.JobParameters
import android.app.job.JobService
import com.tmdb.domain.common.Resource
import com.tmdb.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyJobService : JobService() {
    @Inject
    lateinit var moviesUseCase: GetMoviesUseCase

    override fun onStartJob(params: JobParameters?): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            moviesUseCase(1)
                .onStart { emit(Resource.Loading) }
                .collect()
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}
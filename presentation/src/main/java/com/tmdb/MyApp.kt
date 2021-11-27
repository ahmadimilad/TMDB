package com.tmdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    //region Other => initTimber , ...
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "Timber -> " +
                            "${super.createStackElementTag(element)}:" +
                            "${element.methodName}:" +
                            "${element.lineNumber}"
                }
            })
        }
    }
    //endregion
}
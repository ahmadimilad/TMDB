package com.tmdb

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        //Support Vector - In API less than 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

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
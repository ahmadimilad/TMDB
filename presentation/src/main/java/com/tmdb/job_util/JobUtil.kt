package com.tmdb.job_util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import com.tmdb.services.MyJobService

object JobUtil {
    private const val ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L // 1 Day

    fun scheduleJob(context: Context) {
        val serviceComponent = ComponentName(context, MyJobService::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setPeriodic(ONE_DAY_INTERVAL)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setRequiresBatteryNotLow(true)
        }

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        jobScheduler.schedule(builder.build())
    }
}
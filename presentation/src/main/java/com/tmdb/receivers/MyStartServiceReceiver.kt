package com.tmdb.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tmdb.job_util.JobUtil

class MyStartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                JobUtil.scheduleJob(context)
            }
        }
    }
}
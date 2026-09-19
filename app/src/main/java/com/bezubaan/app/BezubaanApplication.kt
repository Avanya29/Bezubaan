package com.bezubaan.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BezubaanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            val rescueChannel = NotificationChannel(
                "bezubaan_rescue",
                "Rescue Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for urgent animal rescues"
            }
            
            val socialChannel = NotificationChannel(
                "bezubaan_social",
                "Social Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for likes, comments and follows"
            }
            
            val generalChannel = NotificationChannel(
                "bezubaan_general",
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General app updates and notifications"
            }
            
            notificationManager.createNotificationChannels(
                listOf(rescueChannel, socialChannel, generalChannel)
            )
        }
    }
}

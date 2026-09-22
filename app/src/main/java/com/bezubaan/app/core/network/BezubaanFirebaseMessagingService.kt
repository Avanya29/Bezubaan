package com.bezubaan.app.core.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bezubaan.app.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class BezubaanFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New Token: $token")
        // TODO: Send this token to the backend using an API call.
        // In the ViewModel, we can also fetch it via FirebaseMessaging.getInstance().token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        Log.d("FCM", "Message received from: ${message.from}")

        // 1. Show a system notification to wake up the user
        showNotification(message)
        
        // 2. If it's a high-priority emergency, we could also broadcast it locally
        // so if the app is alive but in background, it can bring up the Dialog.
        val type = message.data["type"]
        if (type == "EMERGENCY_RESCUE") {
            // High priority logic
            Log.d("FCM", "Emergency push received! Payload: ${message.data["payload"]}")
        }
    }

    private fun showNotification(message: RemoteMessage) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "emergency_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Emergency Rescues",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Critical alerts for new rescue dispatches"
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Pass data to intent
            putExtra("fcm_emergency_payload", message.data["payload"])
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val title = message.notification?.title ?: "🚨 EMERGENCY DISPATCH"
        val body = message.notification?.body ?: "A critical rescue needs your attention!"

        val notification = NotificationCompat.Builder(this, channelId)
            // .setSmallIcon(R.drawable.ic_launcher_foreground) // Use your app icon
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true) // Wake up screen
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}

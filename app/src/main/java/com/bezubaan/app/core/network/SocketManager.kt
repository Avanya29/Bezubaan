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
import com.bezubaan.app.core.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class SocketManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var socket: Socket? = null
    
    // Flow to emit emergency events to the UI
    private val _emergencyEvents = MutableSharedFlow<JSONObject>(extraBufferCapacity = 10)
    val emergencyEvents: SharedFlow<JSONObject> = _emergencyEvents.asSharedFlow()

    fun connect(jwtToken: String) {
        if (socket?.connected() == true) return

        try {
            // Remove /api from the base URL so Socket.IO connects to the root domain
            val hostUrl = Constants.API_BASE_URL.substringBefore("/api")
            
            val options = IO.Options.builder()
                .setAuth(mapOf("token" to jwtToken))
                .setTransports(arrayOf("websocket"))
                .build()

            socket = IO.socket(hostUrl, options)

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d("SocketManager", "Connected to WebSocket Gateway at $hostUrl")
            }

            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketManager", "Disconnected from WebSocket Gateway")
            }

            socket?.on("new_emergency_rescue") { args ->
                if (args.isNotEmpty()) {
                    val data = args[0] as JSONObject
                    Log.d("SocketManager", "Emergency Rescue Received: $data")
                    _emergencyEvents.tryEmit(data)
                    showEmergencyNotification(data)
                }
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e("SocketManager", "Failed to connect to socket", e)
        }
    }

    private fun showEmergencyNotification(data: JSONObject) {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("fcm_emergency_payload", data.toString())
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val title = "🚨 EMERGENCY RESCUE DISPATCH"
            val description = try {
                data.optString("description").ifBlank {
                    data.optJSONObject("animal")?.optString("species")?.let { "Injured $it reported nearby" }
                        ?: "A critical animal rescue needs your attention!"
                }
            } catch (e: Exception) {
                "A critical animal rescue needs your attention!"
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(Random.nextInt(1000, 9999), notification)
        } catch (e: Exception) {
            Log.e("SocketManager", "Failed to display local emergency notification", e)
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
        Log.d("SocketManager", "Socket manually disconnected")
    }
}

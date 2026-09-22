package com.bezubaan.app.core.network

import android.util.Log
import com.bezubaan.app.core.common.Constants
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor() {
    private var socket: Socket? = null
    
    // Flow to emit emergency events to the UI
    private val _emergencyEvents = MutableSharedFlow<JSONObject>(extraBufferCapacity = 10)
    val emergencyEvents: SharedFlow<JSONObject> = _emergencyEvents.asSharedFlow()

    fun connect(jwtToken: String) {
        if (socket?.connected() == true) return

        try {
            // Remove /api/v1/ from the base URL since Socket.IO connects to the root
            val hostUrl = Constants.API_BASE_URL.replace("/api/v1/", "")
            
            val options = IO.Options.builder()
                .setAuth(mapOf("token" to jwtToken))
                .setTransports(arrayOf("websocket"))
                .build()

            socket = IO.socket(hostUrl, options)

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d("SocketManager", "Connected to WebSocket Gateway")
            }

            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketManager", "Disconnected from WebSocket Gateway")
            }

            socket?.on("new_emergency_rescue") { args ->
                if (args.isNotEmpty()) {
                    val data = args[0] as JSONObject
                    Log.d("SocketManager", "Emergency Rescue Received: $data")
                    _emergencyEvents.tryEmit(data)
                }
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e("SocketManager", "Failed to connect to socket", e)
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
        Log.d("SocketManager", "Socket manually disconnected")
    }
}

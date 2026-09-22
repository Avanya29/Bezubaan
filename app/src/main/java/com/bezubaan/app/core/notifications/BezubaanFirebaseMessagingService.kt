package com.bezubaan.app.core.notifications

// Firebase dependencies are currently commented out.
// This is a placeholder service that will be activated when
// google-services.json is added and Firebase deps are uncommented.
// 
// import com.google.firebase.messaging.FirebaseMessagingService
// import com.google.firebase.messaging.RemoteMessage
//
// When Firebase is enabled, this class should extend FirebaseMessagingService

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Placeholder FCM service. 
 * When Firebase is configured:
 * 1. Add google-services.json to app/
 * 2. Uncomment Firebase dependencies in app/build.gradle.kts
 * 3. Replace this with a real FirebaseMessagingService implementation
 */
class BezubaanFirebaseMessagingService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
}

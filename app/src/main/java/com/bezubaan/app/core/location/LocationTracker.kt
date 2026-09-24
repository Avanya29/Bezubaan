package com.bezubaan.app.core.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}

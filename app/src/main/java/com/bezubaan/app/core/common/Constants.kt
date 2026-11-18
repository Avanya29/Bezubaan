package com.bezubaan.app.core.common

object Constants {
    const val API_BASE_URL = "https://bezubaan-api.onrender.com/api/"
    const val PREFERENCES_NAME = "bezubaan_preferences"
    const val DATABASE_NAME = "bezubaan_db"
    const val JWT_TOKEN_KEY = "jwt_token"

    // Google Sign-In — Web Client ID from Google Cloud Console
    // This must be the "Web application" type client ID, NOT the Android one.
    // The backend uses the same ID (GOOGLE_CLIENT_ID env var) to verify the token.
    const val GOOGLE_WEB_CLIENT_ID = "630553151836-u6ff80c5aoctibjhi15k8e08j3k35glo.apps.googleusercontent.com"
    
    // Roles
    const val ROLE_CITIZEN = "CITIZEN"
    const val ROLE_VOLUNTEER = "VOLUNTEER"
    const val ROLE_NGO = "NGO"
    
    // Urgency Levels
    const val URGENCY_HIGH = "HIGH"
    const val URGENCY_MEDIUM = "MEDIUM"
    const val URGENCY_LOW = "LOW"
}


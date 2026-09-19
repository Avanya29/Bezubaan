package com.bezubaan.app.feature.rescue.domain.model

data class RescueCase(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val status: String,
    val urgency: String
)

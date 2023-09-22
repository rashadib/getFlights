package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class FlightsResponse(
    val `data`: Data,
    val sessionId: String,
    val status: Boolean,
    val timestamp: Long
)
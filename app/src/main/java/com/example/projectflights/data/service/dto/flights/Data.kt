package com.example.projectflights.data.service.dto.flights


data class Data(
    val context: Context,
    val filterStats: FilterStats,
    val flightsSessionId: String,
    val itineraries: List<Itinerary>,
    val messages: List<Any>
)
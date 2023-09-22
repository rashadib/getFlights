package com.example.projectflights.data.service.dto.airports


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    val `data`: List<Data>,
    val status: Boolean,
    val timestamp: Long
)
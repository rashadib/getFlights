package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class Airport(
    val airports: List<AirportX>,
    val city: String
)
package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class FilterStats(
    val airports: List<Airport>,
    val carriers: List<Carrier>,
    val duration: Duration,
    val stopPrices: StopPrices
)
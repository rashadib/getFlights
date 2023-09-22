package com.example.projectflights.data.service.dto.airports


import com.google.gson.annotations.SerializedName

data class Navigation(
    val entityId: String,
    val entityType: String,
    val localizedName: String,
    val relevantFlightParams: RelevantFlightParams,
    val relevantHotelParams: RelevantHotelParams
)
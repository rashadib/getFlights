package com.example.projectflights.data.service.dto.airports


import com.google.gson.annotations.SerializedName

data class RelevantFlightParams(
    val entityId: String,
    val flightPlaceType: String,
    val localizedName: String,
    val skyId: String
)
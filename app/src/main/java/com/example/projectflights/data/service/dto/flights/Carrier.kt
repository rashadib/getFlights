package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class Carrier(
    val id: Int,
    val logoUrl: String,
    val name: String
)
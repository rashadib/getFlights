package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class Direct(
    val formattedPrice: String,
    val isPresent: Boolean
)
package com.example.projectflights.data.service.dto.flights


import com.google.gson.annotations.SerializedName

data class StopPrices(
    val direct: Direct,
    val one: One,
    val twoOrMore: TwoOrMore
)
package com.example.projectflights.data.service.dto.airports


import com.google.gson.annotations.SerializedName

data class Data(
    val entityId: String,
    val navigation: Navigation,
    val presentation: Presentation,
    val skyId: String
)
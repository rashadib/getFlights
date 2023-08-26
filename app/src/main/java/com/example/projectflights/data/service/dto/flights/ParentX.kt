package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParentX(
    val displayCode: String,
    val flightPlaceId: String,
    val name: String,
    val type: String
):Parcelable
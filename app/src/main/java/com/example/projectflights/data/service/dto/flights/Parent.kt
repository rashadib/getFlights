package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parent(
    val displayCode: String,
    val flightPlaceId: String,
    val name: String,
    val type: String
):Parcelable
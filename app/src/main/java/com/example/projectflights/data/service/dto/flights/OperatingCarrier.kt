package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OperatingCarrier(
    val allianceId: Int,
    val alternateId: String,
    val id: Int,
    val name: String
):Parcelable
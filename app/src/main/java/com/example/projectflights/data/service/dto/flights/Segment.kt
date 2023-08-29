package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Segment(
    val arrival: String,
    val departure: String,
    val destination: DestinationX,
    val durationInMinutes: Int,
    val flightNumber: String,
    val id: String,
    val marketingCarrier: MarketingCarrier,
    val operatingCarrier: OperatingCarrier,
    val origin: OriginX
):Parcelable, Serializable
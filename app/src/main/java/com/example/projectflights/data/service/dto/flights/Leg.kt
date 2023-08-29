package com.example.projectflights.data.service.dto.flights
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Leg(
    val arrival: String,
    val carriers: Carriers,
    val departure: String,
    val destination: Destination?,
    val durationInMinutes: Int,
    val id: String,
    val isSmallestStops: Boolean,
    val origin: Origin,
    val segments: List<Segment>? = arrayListOf(),
    val stopCount: Int,
    val timeDeltaInDays: Int
):Parcelable, Serializable
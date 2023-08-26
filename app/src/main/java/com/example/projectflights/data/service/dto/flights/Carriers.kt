package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Carriers(
    val marketing: List<Marketing>,
    val operating: List<Operating>,
    val operationType: String
):Parcelable
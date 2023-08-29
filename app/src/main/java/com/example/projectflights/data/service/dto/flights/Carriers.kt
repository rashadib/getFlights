package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Carriers(
    val marketing: List<Marketing>? = arrayListOf(),
    val operating: List<Operating>? = arrayListOf(),
    val operationType: String? = ""
):Parcelable, Serializable
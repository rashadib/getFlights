package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Price(
    val formatted: String?="",
    val raw: Double?=0.0
):Parcelable, Serializable
package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class FarePolicy(
    val isCancellationAllowed: Boolean?=false,
    val isChangeAllowed: Boolean?=false,
    val isPartiallyChangeable: Boolean?=false,
    val isPartiallyRefundable: Boolean?=false
):Parcelable, Serializable
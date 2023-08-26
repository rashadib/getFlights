package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FarePolicy(
    val isCancellationAllowed: Boolean,
    val isChangeAllowed: Boolean,
    val isPartiallyChangeable: Boolean,
    val isPartiallyRefundable: Boolean
):Parcelable
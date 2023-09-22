package com.example.projectflights.data.service.dto.flights

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Itinerary(
//    val eco: Eco,
    val farePolicy: FarePolicy,
    val hasFlexibleOptions: Boolean?=true,
    val id: String,
    val isMashUp:Boolean?=true,
    val isProtectedSelfTransfer: Boolean,
    val isSelfTransfer: Boolean,
    val legs: List<Leg>?= arrayListOf(),
    val price: Price,
    val score: Double,
    val tags: List<String>?= arrayListOf()
): Parcelable, Serializable
package com.example.projectflights.data.service.dto.flights

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Itinerary(
    val eco: Eco,
    val farePolicy: FarePolicy,
    val hasFlexibleOptions: Boolean,
    val id: String,
    val isMashUp: Boolean,
    val isProtectedSelfTransfer: Boolean,
    val isSelfTransfer: Boolean,
    val legs: List<Leg>,
    val price: Price,
    val score: Double,
    val tags: List<String>
): Parcelable
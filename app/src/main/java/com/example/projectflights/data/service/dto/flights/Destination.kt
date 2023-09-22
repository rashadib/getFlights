package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Destination(
    val city: String?="",
    val displayCode: String?="",
    val id: String?="",
    val isHighlighted: Boolean?=true,
    val name: String?=""
):Parcelable, Serializable
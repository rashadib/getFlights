package com.example.projectflights.data.service.dto.flights


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Marketing(
    val id: Int,
    val logoUrl: String,
    val name: String
):Parcelable, Serializable
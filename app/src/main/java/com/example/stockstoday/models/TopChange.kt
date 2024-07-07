package com.example.stockstoday.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopChange(
    @SerializedName("change_amount")
    val changeAmount: String,
    @SerializedName("change_percentage")
    val changePercentage: String,
    val price: String,
    val ticker: String,
    val volume: String
) : Parcelable

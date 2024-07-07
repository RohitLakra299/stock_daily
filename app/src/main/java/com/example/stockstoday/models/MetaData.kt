package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val refreshed: String,
    @SerializedName("4. Interval")
    val interval: String,
    @SerializedName("5. Output Size")
    val outputSize: String,
    @SerializedName("6. Time Zone")
    val timeZone: String
)
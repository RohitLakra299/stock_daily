package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class ChangeSeries(
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String,
    @SerializedName("6. volume")
    val volume: String,

)

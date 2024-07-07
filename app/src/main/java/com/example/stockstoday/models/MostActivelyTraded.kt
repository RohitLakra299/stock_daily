package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class MostActivelyTraded(
    @SerializedName("change_amount")
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)
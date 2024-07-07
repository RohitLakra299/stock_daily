package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class TopGainerAndLoserModel(
    @SerializedName("last_updated")
    val lastUpdated: String,
    val metadata: String,
    @SerializedName("most_actively_traded")
    val mostActivelyTraded: List<MostActivelyTraded>,
    @SerializedName("top_gainers")
    val topGainers: List<TopChange>,
    @SerializedName("top_losers")
    val topLosers: List<TopChange>
)
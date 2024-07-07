package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class TimeSeriesModel(
    @SerializedName("Meta Data")
    val metaData: MetaData,
    @SerializedName("Time Series (Daily)")
    val timeSeries5min: Map<String, ChangeSeries>,
)
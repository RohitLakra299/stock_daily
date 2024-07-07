package com.example.stockstoday.models

import com.google.gson.annotations.SerializedName

data class SearchModel(
    val bestMatches: List<BestMatches>
)
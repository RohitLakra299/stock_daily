package com.example.stockstoday.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TopLoser")
data class CacheTopLoser(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    val ticker: String,
    val volume: String,
    val lastUpdated : String,
    val timestamp: Long
)
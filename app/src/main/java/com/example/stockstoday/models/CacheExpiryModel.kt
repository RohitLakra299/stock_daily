package com.example.stockstoday.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CacheExpiry")
data class CacheExpiryModel(
    @PrimaryKey(autoGenerate = false)
    val symbol : String,
    val cacheExpiry: Long
)

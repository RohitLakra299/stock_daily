package com.example.stockstoday.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "CacheTime")
data class CacheTime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symbol: String?,
    val timestamp: String?,
    val open: String?,
    val high: String?,
    val low: String?,
    val close: String?,
    val volume: String?,
    val expiryTime: Long
)

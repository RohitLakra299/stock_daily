package com.example.stockstoday.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CompanyDetail")
data class CompanyDetailModel(
    @ColumnInfo(name = "Address") val Address: String?,
    @ColumnInfo(name = "AnalystRatingBuy") val AnalystRatingBuy: String?,
    @ColumnInfo(name = "AnalystRatingHold") val AnalystRatingHold: String?,
    @ColumnInfo(name = "AnalystRatingSell") val AnalystRatingSell: String?,
    @ColumnInfo(name = "AnalystRatingStrongBuy") val AnalystRatingStrongBuy: String?,
    @ColumnInfo(name = "AnalystRatingStrongSell") val AnalystRatingStrongSell: String?,
    @ColumnInfo(name = "AnalystTargetPrice") val AnalystTargetPrice: String?,
    @ColumnInfo(name = "AssetType") val AssetType: String?,
    @ColumnInfo(name = "Beta") val Beta: String?,
    @ColumnInfo(name = "BookValue") val BookValue: String?,
    @ColumnInfo(name = "CIK") val CIK: String?,
    @ColumnInfo(name = "Country") val Country: String?,
    @ColumnInfo(name = "Currency") val Currency: String?,
    @ColumnInfo(name = "Description") val Description: String?,
    @ColumnInfo(name = "DilutedEPSTTM") val DilutedEPSTTM: String?,
    @ColumnInfo(name = "DividendDate") val DividendDate: String?,
    @ColumnInfo(name = "DividendPerShare") val DividendPerShare: String?,
    @ColumnInfo(name = "DividendYield") val DividendYield: String?,
    @ColumnInfo(name = "EBITDA") val EBITDA: String?,
    @ColumnInfo(name = "EPS") val EPS: String?,
    @ColumnInfo(name = "EVToEBITDA") val EVToEBITDA: String?,
    @ColumnInfo(name = "EVToRevenue") val EVToRevenue: String?,
    @ColumnInfo(name = "ExDividendDate") val ExDividendDate: String?,
    @ColumnInfo(name = "Exchange") val Exchange: String?,
    @ColumnInfo(name = "FiscalYearEnd") val FiscalYearEnd: String?,
    @ColumnInfo(name = "ForwardPE") val ForwardPE: String?,
    @ColumnInfo(name = "GrossProfitTTM") val GrossProfitTTM: String?,
    @ColumnInfo(name = "Industry") val Industry: String?,
    @ColumnInfo(name = "LatestQuarter") val LatestQuarter: String?,
    @ColumnInfo(name = "MarketCapitalization") val MarketCapitalization: String?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Symbol") val Symbol: String,
    @ColumnInfo(name = "Name") val Name: String?,
    @ColumnInfo(name = "OperatingMarginTTM") val OperatingMarginTTM: String?,
    @ColumnInfo(name = "PEGRatio") val PEGRatio: String?,
    @ColumnInfo(name = "PERatio") val PERatio: String?,
    @ColumnInfo(name = "PriceToBookRatio") val PriceToBookRatio: String?,
    @ColumnInfo(name = "PriceToSalesRatioTTM") val PriceToSalesRatioTTM: String?,
    @ColumnInfo(name = "ProfitMargin") val ProfitMargin: String?,
    @ColumnInfo(name = "QuarterlyEarningsGrowthYOY") val QuarterlyEarningsGrowthYOY: String?,
    @ColumnInfo(name = "QuarterlyRevenueGrowthYOY") val QuarterlyRevenueGrowthYOY: String?,
    @ColumnInfo(name = "ReturnOnAssetsTTM") val ReturnOnAssetsTTM: String?,
    @ColumnInfo(name = "ReturnOnEquityTTM") val ReturnOnEquityTTM: String?,
    @ColumnInfo(name = "RevenuePerShareTTM") val RevenuePerShareTTM: String?,
    @ColumnInfo(name = "RevenueTTM") val RevenueTTM: String?,
    @ColumnInfo(name = "Sector") val Sector: String?,
    @ColumnInfo(name = "SharesOutstanding") val SharesOutstanding: String?,
    @ColumnInfo(name = "TrailingPE") val TrailingPE: String?
)
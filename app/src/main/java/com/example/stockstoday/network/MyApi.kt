package com.example.stockstoday.network

import com.example.stockstoday.models.CompanyDetailModel
import com.example.stockstoday.models.SearchModel
import com.example.stockstoday.models.TimeSeriesModel
import com.example.stockstoday.models.TopGainerAndLoserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @GET("query")
    suspend fun getTopGainersLosers(
        @Query("function") function : String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apikey : String ) : Response<TopGainerAndLoserModel>

    @GET("query")
    suspend fun getStockDetail(
        @Query("function") function : String = "OVERVIEW",
        @Query("symbol") symbol : String,
        @Query("apikey") apikey:String) : Response<CompanyDetailModel?>

    @GET("query")
    suspend fun getStockIntraday(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol : String,
        @Query("outputsize") outputsize : String = "full",
        @Query("apikey") apikey : String) : Response<TimeSeriesModel>

    @GET("query")
    suspend fun searchStock(
        @Query("function") function : String = "SYMBOL_SEARCH",
        @Query("keywords") keywords : String,
        @Query("apikey") apikey : String) : Response<SearchModel>
}
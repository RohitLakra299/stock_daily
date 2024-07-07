package com.example.stockstoday.repository

import com.example.stockstoday.models.CacheTime
import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.CompanyDetailModel
import com.example.stockstoday.models.SearchModel
import com.example.stockstoday.models.TimeSeriesModel
import com.example.stockstoday.models.TopGainerAndLoserModel
import kotlinx.coroutines.flow.Flow

interface StockRepo {
    suspend fun updateAllGainersAndLosers() : TopGainerAndLoserModel?
    suspend fun updateCompanyDetail(symbol : String) : CompanyDetailModel?
    suspend fun saveTopGainersAndLosers(topGainers : List<CacheTopGainer>, topLosers : List<CacheTopLoser>)
    suspend fun saveCompanyDetail(companyDetail : CompanyDetailModel)
    suspend fun getTopGainers() : Flow<List<CacheTopGainer>>?
    suspend fun getTopLosers() : Flow<List<CacheTopLoser>>?
    suspend fun getCompanyDetailFromCache(name : String) : CompanyDetailModel?
    suspend fun updateTimeSeries(symbol: String) : TimeSeriesModel?
    suspend fun saveTime(cacheTime: List<CacheTime>)
    suspend fun getCacheTime(symbol: String) : Flow<List<CacheTime>>?
    suspend fun getSearchResults(query : String) : SearchModel?
}
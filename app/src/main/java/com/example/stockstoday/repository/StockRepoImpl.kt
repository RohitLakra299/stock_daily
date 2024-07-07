package com.example.stockstoday.repository

import android.util.Log
import com.example.stockstoday.models.CacheTime


import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.CompanyDetailModel
import com.example.stockstoday.models.SearchModel
import com.example.stockstoday.models.TimeSeriesModel
import com.example.stockstoday.models.TopGainerAndLoserModel
import com.example.stockstoday.network.MyApi
import com.example.stockstoday.room.CacheDatabase
import kotlinx.coroutines.flow.Flow

class StockRepoImpl(private val api: MyApi, private val database: CacheDatabase) : StockRepo{
    private val apiKey : String = "J5RGV3OPUNJ6OCCM"
    override suspend fun updateAllGainersAndLosers(): TopGainerAndLoserModel? {
        return try{
            val response = api.getTopGainersLosers(apikey = "Demo")
            if(response.isSuccessful){
                Log.d("@@NetworkGL", response.body().toString())
                response.body()
            }else{
                null
            }
        }catch (e: Exception){
            Log.e("@@NetworkGL", e.toString())
            null
        }
    }



    override suspend fun updateCompanyDetail(symbol: String): CompanyDetailModel? {
        return try {
            val response = api.getStockDetail(symbol = symbol, apikey = apiKey)
            if (response.isSuccessful) {
                Log.d("@@NetworkD", response.body().toString())
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("@@NetworkD", e.toString())
            null
        }
    }

    override suspend fun saveTopGainersAndLosers(
        topGainers: List<CacheTopGainer>,
        topLosers: List<CacheTopLoser>
    ) {
        database.dao().deleteTopGainers()
        database.dao().deleteTopLosers()
        try {
            for(i in topGainers){
                database.dao().storeTopGainers(i)
            }
            for(i in topLosers){
                database.dao().storeTopLosers(i)
            }
            Log.d("@@CacheGL", "Saved")
        } catch (e: Exception) {
            Log.e("@@CacheGL", e.toString())
        }
    }


    override suspend fun saveCompanyDetail(companyDetail: CompanyDetailModel) {
        try {
            database.dao().deleteOldCompanyDetail(companyDetail.Symbol!!)
            database.dao().storeCompanyDetail(companyDetail)
            Log.d("@@CacheD", "Saved")
        } catch (e: Exception) {
            Log.e("@@CacheD", e.toString())
        }
    }


    override suspend fun getTopGainers(): Flow<List<CacheTopGainer>>? {
        return try {
            database.dao().deleteOldTopGainers(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
            Log.d("@@CacheG","Get")
            return database.dao().getTopGainers()
        } catch (e: Exception) {
            Log.e("@@CacheGGET", e.toString())
            return null
        }
    }

    override suspend fun getTopLosers(): Flow<List<CacheTopLoser>>? {
        return try {
            database.dao().deleteOldTopLosers(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
            Log.d("@@CacheL","Get")
            return database.dao().getTopLosers()
        } catch (e: Exception) {
            Log.e("@@CacheLGET", e.toString())
            return null
        }
    }


    override suspend fun getCompanyDetailFromCache(symbol : String): CompanyDetailModel? {
        return try {
            val check = database.dao().isCacheExpired(symbol, System.currentTimeMillis()- 1000*60*60*24)
            if(check){
                database.dao().deleteOldCompanyDetail(symbol)
            }
            Log.d("@@CacheD","Get")
            return database.dao().getCompanyDetail(symbol)
        } catch (e: Exception) {
            Log.e("@@CacheDGET", e.toString())
            return null
        }
    }

    override suspend fun updateTimeSeries(symbol: String): TimeSeriesModel? {
        return try {
            val response = api.getStockIntraday(symbol = symbol, apikey = apiKey)
            if (response.isSuccessful) {
                Log.d("@@NetworkTS", response.body().toString())
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("@@NetworkTS", e.toString())
            null
        }
    }


    override suspend fun saveTime(cacheTime: List<CacheTime>) {
        try {
            database.dao().deleteTime(cacheTime[0].symbol?:"")
            for(i in cacheTime){
                database.dao().saveTime(i)
            }
            Log.d("@@CacheTS", "Saved")
        } catch (e: Exception) {
            Log.e("@@CacheTSGET", e.toString())
        }
    }

    override suspend fun getCacheTime(symbol: String): Flow<List<CacheTime>>? {
        return try{
            database.dao().deleteOldTime(symbol, System.currentTimeMillis()- 1000*60*60*24)
            Log.d("@@CacheTS","Get")
            database.dao().getTime(symbol)
        }catch (e: Exception){
            Log.e("@@CacheTSGET", e.toString())
            null
        }
    }

    override suspend fun getSearchResults(query: String): SearchModel? {
        return try {
            val response = api.searchStock(keywords = query, apikey = apiKey)
            if(response.isSuccessful){
                Log.d("@@NetworkS", response.body().toString())
                return response.body()
            }else{
                null
            }
        }catch (e:Exception){
            null
        }
    }


}
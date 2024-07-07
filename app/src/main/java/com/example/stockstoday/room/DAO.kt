package com.example.stockstoday.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockstoday.models.CacheTime
import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.CompanyDetailModel
import kotlinx.coroutines.flow.Flow


@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeTopGainers(topGainers : CacheTopGainer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeTopLosers(topLosers : CacheTopLoser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeCompanyDetail(companyDetail : CompanyDetailModel) : Long

    @Query("SELECT * FROM TopGainer")
    fun getTopGainers() : Flow<List<CacheTopGainer>>?

    @Query("SELECT * FROM TopLoser")
    fun getTopLosers() : Flow<List<CacheTopLoser>>?

    @Query("SELECT * FROM CompanyDetail WHERE symbol = :symbol")
    fun getCompanyDetail(symbol : String) : CompanyDetailModel?

    @Query("SELECT (cacheExpiry < :currentTime) FROM CacheExpiry WHERE symbol = :symbol")
    fun isCacheExpired(symbol : String, currentTime : Long) : Boolean

    @Query("DELETE FROM TopGainer")
    fun deleteTopGainers()

    @Query("DELETE FROM TopLoser")
    fun deleteTopLosers()

    @Query("DELETE FROM TopGainer WHERE timestamp < :expiryTime")
    fun deleteOldTopGainers(expiryTime : Long)

    @Query("DELETE FROM TopLoser WHERE timestamp < :expiryTime")
    fun deleteOldTopLosers(expiryTime : Long)

    @Query("DELETE FROM CompanyDetail WHERE  symbol = :symbol")
    fun deleteOldCompanyDetail(symbol : String) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTime(cacheTime : CacheTime)

    @Query("SELECT * FROM CacheTime WHERE symbol = :symbol")
    fun getTime(symbol : String) : Flow<List<CacheTime>>?

    @Query("DELETE FROM CacheTime WHERE symbol = :symbol AND timestamp < :currentTime")
    fun deleteOldTime(symbol: String, currentTime: Long) : Int

    @Query("DELETE FROM CacheTime WHERE symbol = :symbol")
    fun deleteTime(symbol: String) : Int
}
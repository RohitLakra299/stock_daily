package com.example.stockstoday.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stockstoday.models.CacheExpiryModel
import com.example.stockstoday.models.CacheTime
import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.CompanyDetailModel


@Database(entities = [CacheTopGainer::class,CacheTopLoser::class,CacheExpiryModel::class,CompanyDetailModel::class,CacheTime::class], version = 2, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun dao(): DAO

    companion object{
        @Volatile
        private var database : CacheDatabase? = null

        @Synchronized
        fun getInstance(ctx : Context): CacheDatabase{
            val tempInstance = database
            if(tempInstance == null){
                synchronized(this){
                    val instance = Room.databaseBuilder(ctx.applicationContext, CacheDatabase::class.java,
                        "FavDataBase")
                        .fallbackToDestructiveMigration()
                        .build()
                    database = instance
                    return database!!
                }
            }else{
                return tempInstance!!
            }
        }
    }
}
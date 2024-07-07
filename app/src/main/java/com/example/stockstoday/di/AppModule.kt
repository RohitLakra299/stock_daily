package com.example.stockstoday.di

import android.app.Application
import androidx.room.RoomDatabase
import com.example.stockstoday.network.MyApi
import com.example.stockstoday.repository.StockRepo
import com.example.stockstoday.repository.StockRepoImpl
import com.example.stockstoday.room.CacheDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun createMyApi(): MyApi {
        return Retrofit.Builder()
            .baseUrl("https://www.alphavantage.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }
    @Provides
    @Singleton
    fun createDatabase(application: Application): CacheDatabase{
        return CacheDatabase.getInstance(application)
    }
    @Provides
    @Singleton
    fun provideStockRepo(api: MyApi, database : CacheDatabase): StockRepo {
        return StockRepoImpl(api,database)
    }
}
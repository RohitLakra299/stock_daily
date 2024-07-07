package com.example.stockstoday.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockstoday.models.CacheTime
import com.example.stockstoday.models.CompanyDetailModel
import com.example.stockstoday.repository.StockRepo
import com.example.stockstoday.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val repository : StockRepo) : ViewModel() {

    private var _dataStateCompanyDetail = MutableLiveData<DataState<CompanyDetailModel?>>()

    private var _dataStateTimeSeries = MutableLiveData<DataState<List<CacheTime>?>>()

    val dataStateCompanyDetail : LiveData<DataState<CompanyDetailModel?>>
        get() = _dataStateCompanyDetail
    val dataStateTimeSeries : LiveData<DataState<List<CacheTime>?>>
        get() = _dataStateTimeSeries

    fun fetchData(symbol: String) {
        viewModelScope.launch {
            getStockDetail(symbol)
            getTimeSeries(symbol)
        }
    }
    private suspend fun getStockDetail(symbol : String) = withContext(Dispatchers.IO){
        try {
            _dataStateCompanyDetail.postValue(DataState.Loading)
            var companyDetailModel = repository.getCompanyDetailFromCache(symbol)
            if(companyDetailModel == null){
                updateCompanyDetail(symbol)
            }else{
                _dataStateCompanyDetail.postValue(DataState.Success(companyDetailModel))
            }
        }catch (e : Exception){
            _dataStateCompanyDetail.postValue(DataState.Error(e))
        }

    }
    private suspend fun getTimeSeries(symbol : String) = withContext(Dispatchers.IO){
        try {
            _dataStateTimeSeries.postValue(DataState.Loading)
            repository.getCacheTime(symbol)!!.first().let {
                if(it.isEmpty()){
                    updateTimeSeries(symbol)
                }else{
                    Log.d("@@Detail", it.toString())
                    _dataStateTimeSeries.postValue(DataState.Success(it))
                }
            }
        }catch (e: Exception){
            _dataStateTimeSeries.postValue(DataState.Error(e))
        }

    }

    private suspend fun updateTimeSeries(symbol : String) = withContext(Dispatchers.IO){
        try {
            val timeSeriesModel = repository.updateTimeSeries(symbol)
            if(timeSeriesModel != null){
                val mapi = timeSeriesModel.timeSeries5min
                var cacheTime = mutableListOf<CacheTime>()
                if(mapi != null){
                    for(i in mapi){
                        Log.d("@@updateTimeSeries", i.value.toString())
                        cacheTime.add(CacheTime(symbol = symbol, timestamp = i.key, open = i.value.open, high = i.value.high, low = i.value.low, close = i.value.close, expiryTime = System.currentTimeMillis(), volume = i.value.volume))
                    }
                }
                Log.d("@@updateTimeSeries1", cacheTime.toString())
                _dataStateTimeSeries.postValue(DataState.Success(cacheTime))
                repository.saveTime(cacheTime)
            }else{
                _dataStateTimeSeries.postValue(DataState.Empty)
            }
        }catch (e:Exception) {
            _dataStateTimeSeries.postValue(DataState.Error(e))
        }
    }

    private suspend fun updateCompanyDetail(symbol : String) = withContext(Dispatchers.IO){
        try {
            val companyDetailModel = repository.updateCompanyDetail(symbol)
            if(companyDetailModel != null){
                repository.saveCompanyDetail(companyDetailModel)
                _dataStateCompanyDetail.postValue(DataState.Success(companyDetailModel))
            }else{
                _dataStateCompanyDetail.postValue(DataState.Empty)
            }
        }catch (e:Exception){
            _dataStateCompanyDetail.postValue(DataState.Error(e))
        }

    }
}
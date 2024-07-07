package com.example.stockstoday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.TopChange
import com.example.stockstoday.repository.StockRepo
import com.example.stockstoday.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel@Inject constructor(private val repository : StockRepo) : ViewModel() {
    private var _dataStateTopChange = MutableLiveData<DataState<List<TopChange>>>()
    val dataStateTopChange : LiveData<DataState<List<TopChange>>>
        get() = _dataStateTopChange
    init {
        CoroutineScope(Dispatchers.IO).launch {
            getAllGainersAndLosers(true)
        }
    }

    suspend fun getAllGainersAndLosers(state : Boolean) = withContext(Dispatchers.IO){
        _dataStateTopChange.postValue(DataState.Loading)
        try{
            if(state){
                repository.getTopGainers()?.first()?.let{
                    if(it == null || it.isEmpty()){
                        updateAllGainersAndLosers(state)
                    }else{
                        var change = mutableListOf<TopChange>()
                        for(i in it){
                            change.add(TopChange(i.changeAmount,i.changePercentage,i.price,i.ticker,i.volume))
                        }
                        _dataStateTopChange.postValue(DataState.Success(change))
                    }
                }
            }else{
                repository.getTopLosers()?.first()?.let{
                    if(it == null || it.isEmpty()){
                        updateAllGainersAndLosers(state)
                    }else{
                        var change = mutableListOf<TopChange>()
                        for(i in it){
                            change.add(TopChange(i.changeAmount,i.changePercentage,i.price,i.ticker,i.volume))
                        }
                        _dataStateTopChange.postValue(DataState.Success(change))
                    }
                }
            }
        }catch (e: Exception){
            _dataStateTopChange.postValue(DataState.Error(e))
        }

    }

    suspend fun updateAllGainersAndLosers(state : Boolean)  = withContext(Dispatchers.IO){
        try {
            val topGainerAndLoserModel = repository.updateAllGainersAndLosers()
            if(topGainerAndLoserModel != null){
                if(state){
                    _dataStateTopChange.postValue(DataState.Success(topGainerAndLoserModel.topGainers))
                }else{
                    _dataStateTopChange.postValue(DataState.Success(topGainerAndLoserModel.topLosers))
                }
                val topGainers = mutableListOf<CacheTopGainer>()
                var idx = 1
                for(i in topGainerAndLoserModel.topGainers){
                    topGainers.add(CacheTopGainer(idx,i.changeAmount,i.changePercentage,i.price,i.ticker,i.volume,topGainerAndLoserModel.lastUpdated,System.currentTimeMillis()))
                    idx++
                }
                val topLosers = mutableListOf<CacheTopLoser>()
                idx = 1
                for(i in topGainerAndLoserModel.topLosers){
                    topLosers.add(CacheTopLoser(idx,i.changeAmount,i.changePercentage,i.price,i.ticker,i.volume,topGainerAndLoserModel.lastUpdated,System.currentTimeMillis()))
                    idx++
                }
                repository.saveTopGainersAndLosers(topGainers,topLosers)
            }else{
                _dataStateTopChange.postValue(DataState.Empty)
            }
        }catch (e: Exception){
            _dataStateTopChange.postValue(DataState.Error(e))
        }

    }
}


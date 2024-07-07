package com.example.stockstoday.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockstoday.models.SearchModel
import com.example.stockstoday.repository.StockRepo
import com.example.stockstoday.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val repository : StockRepo) : ViewModel(){
    private var _dataStateSearchResult = MutableLiveData<DataState<SearchModel>>()
    val dataStateSearchResult : MutableLiveData<DataState<SearchModel>>
        get() = _dataStateSearchResult

    suspend fun getSearchResults(query : String) = withContext(Dispatchers.IO){
        try {
            _dataStateSearchResult.postValue(DataState.Loading)
            val searchModel = repository.getSearchResults(query)
            if(searchModel == null){
                _dataStateSearchResult.postValue(DataState.Empty)
            }else{
                _dataStateSearchResult.postValue(DataState.Success(searchModel))
            }
        }catch (e : Exception){
            _dataStateSearchResult.postValue(DataState.Error(e))
        }
    }
}
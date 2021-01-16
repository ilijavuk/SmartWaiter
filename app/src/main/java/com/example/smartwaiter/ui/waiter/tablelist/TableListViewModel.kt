package com.example.smartwaiter.ui.waiter.tablelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.TableOrderRepository
import hr.foi.air.webservice.model.TableOrder
import kotlinx.coroutines.launch
import retrofit2.Response

class TableListViewModel(
    private val repository: TableOrderRepository
):ViewModel(){
    val myResponse: MutableLiveData<Response<List<TableOrder>>> = MutableLiveData()

    fun getTable(
        //table: String,
        method: String,
        //rezerviran: String
    ){
        viewModelScope.launch {
            val response = repository.getTables(method)
            myResponse.value = response
        }
    }
}
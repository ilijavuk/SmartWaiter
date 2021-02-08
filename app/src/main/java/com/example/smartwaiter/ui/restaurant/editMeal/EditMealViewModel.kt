package com.example.smartwaiter.ui.restaurant.editMeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EditMealViewModel(private val repository: Add_mealRepository): ViewModel() {
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()
    val myResponse3: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse4: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse5: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()
    val myResponse6: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse7: MutableLiveData<Resource<String>> = MutableLiveData()


    fun getMealById(
        table: String,
        method: String,
        id_stavka: String,
    ){
        viewModelScope.launch {
            val response = repository.getMealById(table,method, id_stavka)
            myResponse.value = response
        }
    }

    fun updateMeal(
        table: String,
        method: String,
        mealId: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
    ){
        viewModelScope.launch {
            val response = repository.updateMeal(
                table = table,
                method = method,
                mealId = mealId,
                mealName = mealName,
                mealPrice = mealPrice,
                mealDescription = mealDescription,
                mealPhotoPath = mealPhotoPath,
            )
            myResponse7.value = response
        }
    }
    fun insertTag(
        table: String,
        method: String,
        tag: String,
    ){
        viewModelScope.launch {
            val response = repository.insertTag(
                table = table,
                method = method,
                tag = tag
            )
            myResponse3.value = response
        }
    }

    fun bindTag(
        table: String,
        method: String,
        stavka_id: String,
        tag_id: String
    ){
        viewModelScope.launch {
            val response = repository.bindTag(
                table = table,
                method = method,
                stavka_id = stavka_id,
                tag_id = tag_id
            )
            myResponse4.value = response
        }
    }

    fun getAllTags(
        table: String,
        method: String
    ){
        viewModelScope.launch {
            val response = repository.getAllTags(table,method)
            myResponse2.value = response
        }
    }

    fun tagsByMeal(
        function : String,
        lokal_id : String
    ) {
        viewModelScope.launch {
            val response = repository.tagsByMeal(function,lokal_id)
            myResponse5.value = response
        }
    }

    fun RemoveTagsFromMeal(
        function : String,
        lokal_id : String
    ){
        viewModelScope.launch {
            val response = repository.RemoveTagsFromMeal(function,lokal_id)
            myResponse6.value = response
        }
    }
}
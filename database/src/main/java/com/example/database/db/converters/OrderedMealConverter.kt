package com.example.database.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Order

class OrderedMealConverter {
    var gson = Gson()

    @TypeConverter
    fun mealToString(meal: Meal): String {
        return gson.toJson(meal)
    }

    @TypeConverter
    fun stringToMeal(data: String) : Meal{
        val listType = object : TypeToken<Meal>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun orderToString(order: Order): String {
        return gson.toJson(order)
    }

    @TypeConverter
    fun stringToOrder(data: String) : Order{
        val listType = object : TypeToken<Order>() {}.type
        return gson.fromJson(data, listType)
    }
}
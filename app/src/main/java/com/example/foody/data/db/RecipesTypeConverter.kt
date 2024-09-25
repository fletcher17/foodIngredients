package com.example.foody.data.db

import androidx.room.TypeConverter
import com.example.foody.dataModel.FoodRecipe
import com.example.foody.dataModel.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    private inline fun <reified T> Gson.fromTheJson(data: String): T {
        val fromJson = object : TypeToken<T>() {}.type
        return fromJson(data, fromJson)
    }

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(data: Result): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        return gson.fromTheJson(data)
    }
}
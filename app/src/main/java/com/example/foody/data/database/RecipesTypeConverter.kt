package com.example.foody.data.database

import androidx.room.TypeConverter
import com.example.foody.data.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    // ? convert the list of results to one big string to save in the room database

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String{
        return gson.toJson(foodRecipe)
    }
    @TypeConverter
    fun stringToFoodRecipe(data: String) :FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: com.example.foody.data.models.Result): String{
        return gson.toJson(result)
    }
    @TypeConverter
    fun stringToResult(data: String) :com.example.foody.data.models.Result{
        val listType = object : TypeToken<com.example.foody.data.models.Result>() {}.type
        return gson.fromJson(data, listType)
    }

}
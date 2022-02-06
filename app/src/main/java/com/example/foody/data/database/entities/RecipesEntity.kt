package com.example.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.data.util.Constants.Companion.RECIPES_TABLE
import com.example.foody.data.models.FoodRecipe

// ? making the tables of the database??

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
package com.example.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.data.models.FoodRecipe
import com.example.foody.data.models.Result
import com.example.foody.data.util.Constants

@Entity(tableName = Constants.FAVORITES_TABLE)
class FavoritesEntity(var result: Result) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

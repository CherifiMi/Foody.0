package com.example.foody.data.database

import androidx.room.*
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

// ? write the sql in here

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAll()

    //............favorite recipes dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteOneFavorite(favoritesEntity: FavoritesEntity)

    //@Query("DELETE FROM favorites_table Where id LIKE :itemId ")
    //suspend fun deleteOneFavorite(itemId: Int)
}
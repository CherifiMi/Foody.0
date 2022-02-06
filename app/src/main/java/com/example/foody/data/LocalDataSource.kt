package com.example.foody.data


import com.example.foody.data.database.RecipesDao
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor( private val recipesDao: RecipesDao){

    // ? the methods that dao uses
    // ! local repository

    fun readRecipes(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    //.........................favorites..............
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipes(favoritesEntity)
    }
    suspend fun deleteAllFavorites(){
        recipesDao.deleteAllFavorites()
    }
    suspend fun deleteOneFavorite(favoritesEntity: FavoritesEntity){
        recipesDao.deleteOneFavorite(favoritesEntity)
    }
}
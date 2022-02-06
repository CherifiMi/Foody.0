package com.example.foody.data

import com.example.foody.data.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipesApi: FoodRecipesApi) {

    // ? using remote to contact the api
    // ! remote repository

    suspend fun getRecipes(queries: Map<String, String>) : Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

}
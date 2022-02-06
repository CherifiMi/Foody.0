package com.example.foody.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foody.data.Repository
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipesEntity
import com.example.foody.data.models.FoodRecipe
import com.example.foody.data.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repository: Repository,
    application: Application) :AndroidViewModel(application) {

    // region ROOM
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertRecipes(recipesEntity)
        }

    //.........................favorites..............

    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()

    fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }
    fun deleteAllFavorites(){
        viewModelScope.launch(Dispatchers.IO){
            repository.local.deleteAllFavorites()
        }
    }
    fun deleteOneFavorites(favoritesEntity: FavoritesEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local.deleteOneFavorite(favoritesEntity)
        }
    }
    // endregion

    // region RETROFIT
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String,String>)=viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String,String>)=viewModelScope.launch {
        getSearchRecipesSafeCall(searchQuery)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun getSearchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity= RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when{
            response.message().toString().contains("timeout")->{
                return NetworkResult.Error("Timeout")
            }
            response.code()==402 ->{
                return NetworkResult.Error("API Key is Limited")
            }
            response.body()!!.results.isNullOrEmpty()->{
                return NetworkResult.Error("Recipe not found")
            }
            response.isSuccessful->{
                val foodRecipes=response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
    private fun hasInternetConnection(): Boolean{

            val cm = getApplication<Application>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetwork?:return false
            val capabilities = cm.getNetworkCapabilities(activeNetwork)?:return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else->false
            }
    }
// endregion
}


package com.example.foody.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.data.DataStoreRepository
import com.example.foody.data.util.Constants.Companion.API_KEY
import com.example.foody.data.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.data.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.data.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foody.data.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foody.data.util.Constants.Companion.QUERY_API_KEY
import com.example.foody.data.util.Constants.Companion.QUERY_DIET
import com.example.foody.data.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foody.data.util.Constants.Companion.QUERY_NUMBER
import com.example.foody.data.util.Constants.Companion.QUERY_SEARCH
import com.example.foody.data.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// ? the queries the we ask the api to give
// * like in www.thing.com.[queries][queries][queries]

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var backToBottomSheet = true

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDiet(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun changeBackToBottomSheet(backToBS: Boolean) {
        backToBottomSheet = backToBS
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect{ value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}
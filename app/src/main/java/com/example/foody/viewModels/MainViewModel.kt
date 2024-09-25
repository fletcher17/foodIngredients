package com.example.foody.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foody.data.Repository
import com.example.foody.data.db.entities.FavoritesEntity
import com.example.foody.data.db.entities.FoodJokeEntity
import com.example.foody.data.db.entities.RecipesEntity
import com.example.foody.dataModel.FoodJoke
import com.example.foody.dataModel.FoodRecipe
import com.example.foody.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** ROOM DATABASE */
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipe().asLiveData()
    val readFoodJoke: LiveData<FoodJokeEntity> = repository.local.readFoodJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch {
            repository.local.insertRecipes(recipesEntity)
        }
    }

    fun insertFavoriteRecipe(favoriteRecipeEntity: FavoritesEntity) {
        viewModelScope.launch {
            repository.local.insertFavoriteRecipes(favoriteRecipeEntity)
        }
    }

    private fun insertFoodJoke(foodJoke: FoodJokeEntity) {
        viewModelScope.launch {
            repository.local.insertFoodJoke(foodJoke)
        }
    }

    fun deleteFavoriteRecipe(favoriteRecipeEntity: FavoritesEntity) {
        viewModelScope.launch {
            repository.local.deleteFavoriteRecipe(favoriteRecipeEntity)
        }
    }

    fun deleteAllFavoriteRecipes() {
        viewModelScope.launch {
            repository.local.deleteAllFavoriteRecipe()
        }
    }


    /** RETROFIT */
    val recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val searchedRecipesResponse: MutableStateFlow<NetworkResult<FoodRecipe>> = MutableStateFlow(NetworkResult.Loading())
    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) {
        viewModelScope.launch {
            getRecipesSafeCall(queries)
        }
    }

    fun searchRecipes(searchQuery: Map<String, String>) {
        viewModelScope.launch {
            searchRecipesSafeCall(searchQuery)
        }
    }

    fun getFoodJoke(apiKey: String) {
        viewModelScope.launch {
            getFoodJokesSafeCall(apiKey)
        }
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnections()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipesResponse(response)

                val foodRecipe = recipesResponse.value?.data
                if (foodRecipe != null) {
                    offlineCache(foodRecipe)
                }

            } catch (e: Throwable) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnections()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchedRecipesResponse.value = handleRecipesResponse(response)!!

            } catch (e: Throwable) {
                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private suspend fun getFoodJokesSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnections()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if (foodJoke != null) {
                    offlineCacheFoodJoke(foodJoke)
                }

            } catch (e: Throwable) {
                foodJokeResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCache(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun handleRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()!!
                return NetworkResult.Success(foodRecipe)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()!!
                return NetworkResult.Success(foodJoke)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnections(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }



}
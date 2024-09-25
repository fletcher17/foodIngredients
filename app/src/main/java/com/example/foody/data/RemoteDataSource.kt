package com.example.foody.data

import com.example.foody.data.network.FoodRecipesApi
import com.example.foody.dataModel.FoodJoke
import com.example.foody.dataModel.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>) : Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(queries: Map<String, String>) : Response<FoodRecipe> {
        return foodRecipeApi.searchRecipes(queries)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodRecipeApi.getFoodJoke(apiKey)
    }

}
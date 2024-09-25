package com.example.foody.data

import androidx.lifecycle.asFlow
import com.example.foody.data.db.RecipesDao
import com.example.foody.data.db.entities.FavoritesEntity
import com.example.foody.data.db.entities.FoodJokeEntity
import com.example.foody.data.db.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipe(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipe()
    }

    fun readFoodJoke(): Flow<FoodJokeEntity> {
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        return recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipes(favoriteRecipeEntity: FavoritesEntity) {
        return recipesDao.insertFavoriteRecipe(favoriteRecipeEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        return recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteRecipeEntity: FavoritesEntity) {
        return recipesDao.deleteFavoriteRecipe(favoriteRecipeEntity)
    }

    suspend fun deleteAllFavoriteRecipe() {
        return recipesDao.deleteAllFavoriteRecipe()
    }
}
package com.example.foody.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foody.data.db.entities.FavoritesEntity
import com.example.foody.data.db.entities.FoodJokeEntity
import com.example.foody.data.db.entities.RecipesEntity

@Database(entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}
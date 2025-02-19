package com.example.foody.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.dataModel.FoodRecipe
import com.example.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
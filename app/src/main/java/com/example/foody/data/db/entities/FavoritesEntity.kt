package com.example.foody.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.dataModel.Result
import com.example.foody.util.Constants.Companion.FAVORITES_RECIPES_TABLE

@Entity(tableName = FAVORITES_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
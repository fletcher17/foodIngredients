package com.example.foody.dataModel


import com.google.gson.annotations.SerializedName


data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)
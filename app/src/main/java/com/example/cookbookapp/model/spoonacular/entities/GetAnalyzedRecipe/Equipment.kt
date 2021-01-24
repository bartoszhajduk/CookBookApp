package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe


data class Equipment(
    val id: Int,
    val image: String,
    val name: String,
    val temperature: Temperature
)
package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe

import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val id: Int,
    val image: String,
    val name: String,
    val temperature: Temperature
)
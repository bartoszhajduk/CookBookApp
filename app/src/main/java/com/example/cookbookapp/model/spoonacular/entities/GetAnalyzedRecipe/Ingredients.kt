package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe

import kotlinx.serialization.Serializable

@Serializable
data class Ingredients (
        val id: Int,
        val image: String,
        var name: String
)
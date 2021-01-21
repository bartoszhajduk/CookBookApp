package com.example.cookbookapp.model.entities.GetAnalyzedRecipe

data class IngredientsWithAmount(
        val id: Int,
        val title: String,
        val extendedIngredients: List<ExtendedIngredient>
)
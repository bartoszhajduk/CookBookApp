package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe

data class IngredientsWithAmount(
        val id: Int,
        val title: String,
        val extendedIngredients: List<ExtendedIngredient>
)
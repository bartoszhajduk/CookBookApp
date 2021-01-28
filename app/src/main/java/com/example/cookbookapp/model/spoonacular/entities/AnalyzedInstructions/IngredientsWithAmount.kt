package com.example.cookbookapp.model.spoonacular.entities.AnalyzedInstructions

import com.example.cookbookapp.model.spoonacular.entities.SharedEntities.ExtendedIngredient

data class IngredientsWithAmount(
        val id: Int,
        val title: String,
        val extendedIngredients: List<ExtendedIngredient>
)
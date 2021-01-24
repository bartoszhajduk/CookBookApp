package com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe

import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.AnalyzedInstructions
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.ExtendedIngredient

data class ExtractedRecipe (
    val extendedIngredients: List<ExtendedIngredient>,
    val analyzedInstructions: List<AnalyzedInstructions>,
    val readyInMinutes: Int,
    val title: String,
    val servings: Int,
    val image: String
)
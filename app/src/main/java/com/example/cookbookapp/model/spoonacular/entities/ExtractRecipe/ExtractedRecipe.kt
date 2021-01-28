package com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe

import com.example.cookbookapp.model.spoonacular.entities.AnalyzedInstructions.AnalyzedInstructions
import com.example.cookbookapp.model.spoonacular.entities.SharedEntities.ExtendedIngredient

data class ExtractedRecipe (
        val extendedIngredients: List<ExtendedIngredient>,
        val analyzedInstructions: List<AnalyzedInstructions>,
        val readyInMinutes: Int,
        val title: String,
        val servings: Int,
        val image: String
)
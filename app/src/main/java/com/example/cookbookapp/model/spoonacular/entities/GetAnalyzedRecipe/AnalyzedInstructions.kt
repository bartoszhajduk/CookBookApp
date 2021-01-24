package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe

data class AnalyzedInstructions(
    val name: String,
    val steps: List<Step>
)
package com.example.cookbookapp.model.entities.GetAnalyzedRecipe

data class AnalyzedRecipe(
    val name: String,
    val steps: List<Step>
)
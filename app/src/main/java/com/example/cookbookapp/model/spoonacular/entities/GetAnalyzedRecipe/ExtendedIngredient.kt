package com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe

data class ExtendedIngredient (
        var id: Int,
        val measures: Measure,
        val name: String
)
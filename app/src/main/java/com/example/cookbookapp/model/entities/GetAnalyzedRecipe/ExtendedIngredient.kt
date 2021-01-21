package com.example.cookbookapp.model.entities.GetAnalyzedRecipe

data class ExtendedIngredient (
        var id: Int,
        val measures: Measure,
        val name: String
)
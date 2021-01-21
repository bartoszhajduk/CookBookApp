package com.example.cookbookapp.model.entities.GetAnalyzedRecipe

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredients>,
    var number: Int,
    val step: String
)
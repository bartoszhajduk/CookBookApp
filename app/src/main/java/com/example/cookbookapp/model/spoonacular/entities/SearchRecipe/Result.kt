package com.example.cookbookapp.model.spoonacular.entities.SearchRecipe

data class Result (
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String
)
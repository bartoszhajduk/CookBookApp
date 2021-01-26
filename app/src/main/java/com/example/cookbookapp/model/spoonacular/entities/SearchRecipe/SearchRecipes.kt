package com.example.cookbookapp.model.spoonacular.entities.SearchRecipe

data class SearchRecipes (
        val results: List<Result>,
        val baseUri: String
        )
package com.example.cookbookapp.model.spoonacular.entities.SharedEntities

import kotlinx.serialization.Serializable

@Serializable
data class Step(
        val equipment: List<Equipment>,
        val ingredients: List<Ingredients>,
        var number: Int,
        val step: String
)
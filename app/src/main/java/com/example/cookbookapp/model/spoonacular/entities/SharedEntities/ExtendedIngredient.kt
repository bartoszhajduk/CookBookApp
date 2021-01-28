package com.example.cookbookapp.model.spoonacular.entities.SharedEntities

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedIngredient (
        var id: Int,
        val measures: Measure,
        val name: String
)
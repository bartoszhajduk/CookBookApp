package com.example.cookbookapp.model.spoonacular.entities.AnalyzedInstructions

import com.example.cookbookapp.model.spoonacular.entities.SharedEntities.Step

data class AnalyzedInstructions(
    val name: String,
    val steps: List<Step>
)
package com.example.cookbookapp.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.ExtendedIngredient
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.Step

@Entity(tableName = "favourite_recipes_table")
data class FavouriteRecipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val image: String,
    val extendedIngredients: List<ExtendedIngredient>,
    val steps: List<Step>
)
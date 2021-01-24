package com.example.cookbookapp.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_recipes")
data class FavouriteRecipe (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
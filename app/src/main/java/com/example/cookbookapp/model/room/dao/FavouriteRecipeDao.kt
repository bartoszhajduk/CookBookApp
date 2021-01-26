package com.example.cookbookapp.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cookbookapp.model.room.entities.FavouriteRecipe

@Dao
interface FavouriteRecipeDao {
    @Query("SELECT * FROM favourite_recipes_table")
    fun getFavouriteRecipeList(): LiveData<List<FavouriteRecipe>>

    @Insert
    suspend fun insert(favouriteRecipe: FavouriteRecipe)

    @Delete
    suspend fun delete(favouriteRecipe: FavouriteRecipe)
}
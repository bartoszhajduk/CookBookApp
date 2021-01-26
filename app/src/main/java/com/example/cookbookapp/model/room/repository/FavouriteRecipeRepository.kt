package com.example.cookbookapp.model.room.repository

import androidx.lifecycle.liveData
import com.example.cookbookapp.model.room.dao.FavouriteRecipeDao
import com.example.cookbookapp.model.room.entities.FavouriteRecipe

class FavouriteRecipeRepository(private val favouriteRecipeDao: FavouriteRecipeDao) {
    fun getFavouriteRecipeList() = liveData {
        emitSource(favouriteRecipeDao.getFavouriteRecipeList())
    }

    suspend fun add(favouriteRecipe: FavouriteRecipe) {
        favouriteRecipeDao.insert(favouriteRecipe)
    }

    suspend fun delete(favouriteRecipe: FavouriteRecipe) {
        favouriteRecipeDao.delete(favouriteRecipe)
    }
}
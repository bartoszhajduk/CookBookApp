package com.example.cookbookapp.viewmodel.favourite_recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookbookapp.model.room.DataBase
import com.example.cookbookapp.model.room.entities.FavouriteRecipe
import com.example.cookbookapp.model.room.repository.FavouriteRecipeRepository
import kotlinx.coroutines.launch

class FavouriteRecipesViewModel(application: Application): AndroidViewModel(application) {
    val favouriteRecipes: LiveData<List<FavouriteRecipe>>

    private val favouriteRecipeRepository: FavouriteRecipeRepository =
            FavouriteRecipeRepository(DataBase.getDatabase(application).favouriteRecipeDao())

    private var _currentFavouriteRecipe: MutableLiveData<FavouriteRecipe> = MutableLiveData()
    val currentFavouriteRecipe: LiveData<FavouriteRecipe>
        get() = _currentFavouriteRecipe

    init {
        favouriteRecipes = favouriteRecipeRepository.getFavouriteRecipeList()
    }

    fun addFavouriteRecipe(favouriteRecipe: FavouriteRecipe)
    {
        viewModelScope.launch {
            favouriteRecipeRepository.add(favouriteRecipe)
        }
    }

    fun deleteFavouriteRecipe(favouriteRecipe: FavouriteRecipe)
    {
        viewModelScope.launch {
            favouriteRecipeRepository.delete(favouriteRecipe)
        }
    }

    fun setCurrentFavouriteRecipe(favouriteRecipe: FavouriteRecipe)
    {
        _currentFavouriteRecipe.value = favouriteRecipe
    }

//    fun getFavouriteRecipeList()
//    {
//        viewModelScope.launch {
//            favouriteRecipes = favouriteRecipeRepository.getFavouriteRecipeList()
//        }
//    }
}
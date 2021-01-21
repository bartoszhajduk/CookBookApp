package com.example.cookbookapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.IngredientsWithAmount
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.Step
import com.example.cookbookapp.model.entities.SearchRecipe.SearchRecipes
import com.example.cookbookapp.model.repositories.SearchRecipesRepository
import kotlinx.coroutines.launch

class SearchRecipeViewModel(application: Application): AndroidViewModel(application) {
    private val _allRecipes: MutableLiveData<SearchRecipes> = MutableLiveData()
    val allRecipes: LiveData<SearchRecipes>
        get() = _allRecipes

    private val _recipeDetails: MutableLiveData<List<Step>> = MutableLiveData()
    val recipeDetails: LiveData<List<Step>>
        get() = _recipeDetails

    private val _recipeIngredientWithAmounts: MutableLiveData<IngredientsWithAmount> = MutableLiveData()
    val recipeIngredientWithAmounts: LiveData<IngredientsWithAmount>
        get() = _recipeIngredientWithAmounts

    fun getAllRecipesByKeyword(newKeyWord: String)
    {
        viewModelScope.launch {
            _allRecipes.value = SearchRecipesRepository.getAllRecipesByKeyword(newKeyWord)
        }
    }

    fun getAnalyzedRecipe(id: Int)
    {
        viewModelScope.launch {
            val tmp = SearchRecipesRepository.getAnalyzedRecipe(id)
            val flattenedList: MutableList<Step> = mutableListOf()

            flattenedList.addAll(tmp[0].steps)
            for (i in 1..tmp.size - 1)
            {
                for(j in 0..tmp[i].steps.size - 1)
                {
                    tmp[i].steps[j].number = tmp[i - 1].steps[tmp[i - 1].steps.size - 1].number + j
                }
                flattenedList.addAll(tmp[i].steps)
            }
            _recipeDetails.value = flattenedList
        }
    }

    fun getIngredientsWithAmount(id: Int)
    {
        viewModelScope.launch {
            _recipeIngredientWithAmounts.value = SearchRecipesRepository.getIngredientsWithAmount(id)
        }
    }
}
package com.example.cookbookapp.viewmodel.extract_recipe

import android.app.Application
import androidx.lifecycle.*
import com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe.ExtractedRecipe
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.Step
import com.example.cookbookapp.model.spoonacular.repository.SearchRecipesRepository
import kotlinx.coroutines.launch

class ExtractRecipeViewModel(application: Application): AndroidViewModel(application) {
    private var _extractedRecipe: MutableLiveData<ExtractedRecipe> = MutableLiveData()
    val extractedRecipe: LiveData<ExtractedRecipe>
        get() = _extractedRecipe

    private var _steps: MutableLiveData<List<Step>> = MutableLiveData()
    val steps: LiveData<List<Step>>
        get() = _steps

    private var _recipeUrl: String = ""
    val recipeUrl: String
        get() = _recipeUrl

    fun getExtractedRecipe()
    {
        viewModelScope.launch {
            _extractedRecipe.value = SearchRecipesRepository.getExtractedRecipe(recipeUrl)

            val tmp =  _extractedRecipe.value!!.analyzedInstructions
            val flattenedList: MutableList<Step> = mutableListOf()

            if(tmp.size != 0)
            {
                flattenedList.addAll(tmp[0].steps)
                for (i in 1..tmp.size - 1)
                {
                    for(j in 0..tmp[i].steps.size - 1)
                    {
                        tmp[i].steps[j].number = tmp[i - 1].steps[tmp[i - 1].steps.size - 1].number + j
                    }
                    flattenedList.addAll(tmp[i].steps)
                }
            }
            _steps.value = flattenedList
        }
    }

    fun setUrl(newUrl: String)
    {
        _recipeUrl = newUrl
    }
}
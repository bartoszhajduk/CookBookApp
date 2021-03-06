package com.example.cookbookapp.viewmodel.extract_recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe.ExtractedRecipe
import com.example.cookbookapp.model.spoonacular.entities.AnalyzedInstructions.AnalyzedInstructions
import com.example.cookbookapp.model.spoonacular.entities.SharedEntities.Step
import com.example.cookbookapp.model.spoonacular.repository.SearchRecipesRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ExtractRecipeViewModel(application: Application): AndroidViewModel(application) {
    private var _extractedRecipe: MutableLiveData<ExtractedRecipe> = MutableLiveData()
    val extractedRecipe: LiveData<ExtractedRecipe>
        get() = _extractedRecipe

    private var _steps: MutableLiveData<List<Step>> = MutableLiveData()
    val steps: LiveData<List<Step>>
        get() = _steps

    private var recipeUrl: String = ""

    fun getExtractedRecipe()
    {
        viewModelScope.launch {
            try {
                _extractedRecipe.value = SearchRecipesRepository.getExtractedRecipe(recipeUrl)
            }
            catch (e: SocketTimeoutException)
            {
                Log.d("URL ERROR", "Wrong url")
            }

            var tmp = listOf<AnalyzedInstructions>()
            if (_extractedRecipe.value?.analyzedInstructions != null)
            {
                tmp = _extractedRecipe.value!!.analyzedInstructions.toList()
            }
            val flattenedList: MutableList<Step> = mutableListOf()

            if(tmp.isNotEmpty())
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
        recipeUrl = newUrl
    }
}
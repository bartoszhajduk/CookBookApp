package com.example.cookbookapp.model.repositories

import com.example.cookbookapp.model.entities.SearchRecipe.SearchRecipes
import com.example.cookbookapp.model.API.SearchRecipesService
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.AnalyzedRecipe
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.IngredientsWithAmount
import retrofit2.awaitResponse

class SearchRecipesRepository {
    companion object {
        suspend fun getAllRecipesByKeyword(keyword: String): SearchRecipes {
            return SearchRecipesService.api.getAllRecipiesByKeyword(keyword).
                    awaitResponse().body()?: SearchRecipes(emptyList(),"")
        }

        suspend fun getAnalyzedRecipe(id: Int): List<AnalyzedRecipe> {
            return SearchRecipesService.api.getAnalyzedRecipe(id).
                    awaitResponse().body()?: emptyList()
        }

        suspend fun getIngredientsWithAmount(id: Int): IngredientsWithAmount {
            return SearchRecipesService.api.getIngredientsForRecipe(id).
                    awaitResponse().body()?: IngredientsWithAmount(-1,"", emptyList())
        }
    }
}
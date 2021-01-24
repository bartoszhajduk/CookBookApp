package com.example.cookbookapp.model.spoonacular.repository

import com.example.cookbookapp.model.spoonacular.entities.SearchRecipe.SearchRecipes
import com.example.cookbookapp.model.spoonacular.API.SearchRecipesService
import com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe.ExtractedRecipe
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.AnalyzedInstructions
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.IngredientsWithAmount
import retrofit2.awaitResponse

class SearchRecipesRepository {
    companion object {
        suspend fun getAllRecipesByKeyword(keyword: String): SearchRecipes {
            return SearchRecipesService.api.getAllRecipiesByKeyword(keyword).
                    awaitResponse().body()?: SearchRecipes(emptyList(),"")
        }

        suspend fun getAnalyzedRecipe(id: Int): List<AnalyzedInstructions> {
            return SearchRecipesService.api.getAnalyzedRecipe(id).
                    awaitResponse().body()?: emptyList()
        }

        suspend fun getIngredientsWithAmount(id: Int): IngredientsWithAmount {
            return SearchRecipesService.api.getIngredientsForRecipe(id).
                    awaitResponse().body()?: IngredientsWithAmount(-1,"", emptyList())
        }

        suspend fun getExtractedRecipe(url: String): ExtractedRecipe {
            return SearchRecipesService.api.getExtractedRecipe(url).
                    awaitResponse().body()?:
                        ExtractedRecipe(
                            emptyList(),
                            emptyList(),
                            0,
                            "",
                            0,
                            ""
                        )
        }
    }
}
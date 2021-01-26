package com.example.cookbookapp.model.spoonacular.API

import com.example.cookbookapp.model.spoonacular.entities.ExtractRecipe.ExtractedRecipe
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.AnalyzedInstructions
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.IngredientsWithAmount
import com.example.cookbookapp.model.spoonacular.entities.SearchRecipe.SearchRecipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchRecipesAPI {
    @GET("recipes/search/?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getAllRecipiesByKeyword(@Query("query") query: String): Call<SearchRecipes>

    @GET("recipes/{id}/analyzedInstructions?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getAnalyzedRecipe(@Path("id") id: Int): Call<List<AnalyzedInstructions>>

    @GET("recipes/{id}/information?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getIngredientsForRecipe(@Path("id") id: Int): Call<IngredientsWithAmount>

    @GET("recipes/extract?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getExtractedRecipe(@Query("url") url: String): Call<ExtractedRecipe>
}
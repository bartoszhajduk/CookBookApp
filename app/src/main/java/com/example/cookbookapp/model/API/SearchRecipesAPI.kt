package com.example.cookbookapp.model.API

import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.AnalyzedRecipe
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.IngredientsWithAmount
import com.example.cookbookapp.model.entities.SearchRecipe.SearchRecipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchRecipesAPI {
    @GET("recipes/search/?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getAllRecipiesByKeyword(@Query("query") query: String): Call<SearchRecipes>

    @GET("recipes/{id}/analyzedInstructions?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getAnalyzedRecipe(@Path("id") id: Int): Call<List<AnalyzedRecipe>>

    @GET("recipes/{id}/information?apiKey=c2ba9a8a7f9a460eadcbab9126a1ee6b")
    fun getIngredientsForRecipe(@Path("id") id: Int): Call<IngredientsWithAmount>

//    @GET("station/findAll")
//    fun getSearchedRecipes(): Call<List<SearchRecipes>>

//    @GET("station/sensors/{stationId}")
//    fun getAllStationSensorsById(@Path("stationId") stationId :Int): Call<List<Sensor>>
//
//    @GET("data/getData/{sensorId}")
//    fun getSensorDataById(@Path("sensorId") sensorId:Int): Call<Data>

}
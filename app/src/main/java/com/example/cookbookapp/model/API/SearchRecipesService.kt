package com.example.cookbookapp.model.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://api.spoonacular.com/"

object SearchRecipesService {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SearchRecipesAPI by lazy {
        retrofit.create(SearchRecipesAPI::class.java)
    }
}
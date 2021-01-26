package com.example.cookbookapp.model.spoonacular.API

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.jvm.Throws

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
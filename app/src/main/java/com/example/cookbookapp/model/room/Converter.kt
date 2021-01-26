package com.example.cookbookapp.model.room

import androidx.room.TypeConverter
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.Equipment
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.ExtendedIngredient
import com.example.cookbookapp.model.spoonacular.entities.GetAnalyzedRecipe.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()
class Converter {
    @TypeConverter
    fun extendedIngredientsListToString(list: List<ExtendedIngredient>): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToExtendedIngredientsList(string: String): List<ExtendedIngredient>? {
        val itemType = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.fromJson<List<ExtendedIngredient>>(string, itemType)
    }

    @TypeConverter
    fun stepListToString(list: List<Step>): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToStepList(string: String): List<Step>? {
        val itemType = object : TypeToken<List<Step>>() {}.type
        return gson.fromJson<List<Step>>(string, itemType)
    }

//    @TypeConverter
//    fun equipmentListToString(list: List<Equipment>): String? {
//        return gson.toJson(list)
//    }
//
//    @TypeConverter
//    fun stringToEquipmentList(string: String): List<Equipment>? {
//        return gson.fromJson<List<Equipment>>(string, Equipment::class.java)
//    }
}
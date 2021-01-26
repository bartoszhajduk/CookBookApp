package com.example.cookbookapp.model.room

import android.content.Context
import androidx.room.*
import com.example.cookbookapp.model.room.dao.FavouriteRecipeDao
import com.example.cookbookapp.model.room.entities.FavouriteRecipe
import kotlinx.serialization.json.Json

@Database(entities = [FavouriteRecipe::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class DataBase: RoomDatabase() {

    abstract fun favouriteRecipeDao(): FavouriteRecipeDao

    companion object{
        @Volatile
        private var INSTANCE: DataBase ?= null

        fun getDatabase(context: Context): DataBase {
            val tempInstance= INSTANCE

            if(tempInstance!=null)
                return tempInstance
            else
                synchronized(this)
                {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "my_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}
package com.example.cookbookapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cookbookapp.viewmodel.extract_recipe.ExtractRecipeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home,
                R.id.navigation_search_food,
                R.id.navigation_extract_recipe,
                R.id.navigation_search_food_details,
                R.id.navigation_extract_recipe_details))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if(intent?.action == Intent.ACTION_SEND )
        {
            if ("text/plain" == intent.type) {
                val extractRecipeViewModel =
                    ViewModelProvider(this).get(ExtractRecipeViewModel::class.java)
                intent.getStringExtra(Intent.EXTRA_TEXT)?.let { extractRecipeViewModel.setUrl(it) }
                extractRecipeViewModel.getExtractedRecipe()
                navController.navigate(R.id.navigation_extract_recipe)
                navController.navigate(R.id.navigation_extract_recipe_details)
            }
        }
    }
}

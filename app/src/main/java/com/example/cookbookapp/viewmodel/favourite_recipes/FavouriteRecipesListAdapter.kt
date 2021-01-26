package com.example.cookbookapp.viewmodel.favourite_recipes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R

class FavouriteRecipesListAdapter (var favouriteRecipesViewModel: FavouriteRecipesViewModel):
    RecyclerView.Adapter<FavouriteRecipesListAdapter.FavouriteRecipesHolder>()
{
    inner class FavouriteRecipesHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipesHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_search_recipes_row,
            parent,
            false
        )
        return FavouriteRecipesHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteRecipesHolder, position: Int) {
        val textViewFavouriteRecipeTitle =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchTitle)
        val textViewFavouriteRecipeReadyInMinutes =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchReadyInMinutes)
        val textViewFavouriteRecipeServings =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchServings)
        val imageViewFavouriteRecipe =
                holder.itemView.findViewById<ImageView>(R.id.recipeSearchImage)

        textViewFavouriteRecipeTitle.text =
                favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.title ?: ""
        textViewFavouriteRecipeReadyInMinutes.text =
                favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.readyInMinutes.toString()
        textViewFavouriteRecipeServings.text =
                favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.servings.toString()
        try
        {
            Glide.with(imageViewFavouriteRecipe.context)
                    .asBitmap()
                    .load(favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.image)
                    .into(imageViewFavouriteRecipe)
        }
        catch(e: Exception)
        {
            imageViewFavouriteRecipe.setImageResource(R.drawable.no_image_available)
            Log.d("ERROR", "Nie działa :c")
        }

        holder.itemView.setOnClickListener {
            favouriteRecipesViewModel.favouriteRecipes.value?.let { favouriteRecipeList ->
                favouriteRecipesViewModel.setCurrentFavouriteRecipe(
                        favouriteRecipeList[position]
                )
            }
            holder.itemView.findNavController().navigate(
                    R.id.action_fragment_favourite_recipes_to_fragment_favourite_recipe_details)
        }
    }

    override fun getItemCount(): Int {
        return favouriteRecipesViewModel.favouriteRecipes.value?.size?: 0
    }
}
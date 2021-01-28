package com.example.cookbookapp.viewmodel.favourite_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R

class FavouriteRecipesListAdapter (private val favouriteRecipesViewModel: FavouriteRecipesViewModel):
    RecyclerView.Adapter<FavouriteRecipesListAdapter.FavouriteRecipesHolder>()
{
    inner class FavouriteRecipesHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipesHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_search_recipes_row,
            parent,
            false
        )
        return FavouriteRecipesHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteRecipesHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.recipeSearchImage)
        val title = holder.itemView.findViewById<TextView>(R.id.recipeSearchTitle)
        val readyInMinutes = holder.itemView.findViewById<TextView>(R.id.recipeSearchReadyInMinutes)
        val servings = holder.itemView.findViewById<TextView>(R.id.recipeSearchServings)

        Glide.with(image.context)
                .asBitmap()
                .placeholder(R.drawable.no_image_available)
                .load(favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.image)
                .into(image)
        title.text = favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.title ?: ""
        readyInMinutes.text = favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.readyInMinutes.toString()
        servings.text = favouriteRecipesViewModel.favouriteRecipes.value?.get(position)?.servings.toString()

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
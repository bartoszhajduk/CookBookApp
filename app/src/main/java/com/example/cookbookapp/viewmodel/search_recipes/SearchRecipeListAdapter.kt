package com.example.cookbookapp.viewmodel.search_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R

class SearchRecipeListAdapter (private val searchRecipeViewModel: SearchRecipeViewModel):
    RecyclerView.Adapter<SearchRecipeListAdapter.SearchRecipeHolder>()
{
    inner class SearchRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_search_recipes_row,
            parent,
            false
        )
        return SearchRecipeHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecipeHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.recipeSearchImage)
        val title = holder.itemView.findViewById<TextView>(R.id.recipeSearchTitle)
        val readyInMinutes = holder.itemView.findViewById<TextView>(R.id.recipeSearchReadyInMinutes)
        val servings = holder.itemView.findViewById<TextView>(R.id.recipeSearchServings)

        val joinedImageURL = searchRecipeViewModel.allRecipes.value?.baseUri +
                searchRecipeViewModel.allRecipes.value?.results?.get(position)?.image
        Glide.with(image.context)
                .asBitmap()
                .placeholder(R.drawable.no_image_available)
                .load(joinedImageURL)
                .into(image)
        title.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.title
        readyInMinutes.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.readyInMinutes?.toString()
        servings.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.servings?.toString()

        holder.itemView.setOnClickListener {
            searchRecipeViewModel.setCurrentIndex(position)
            searchRecipeViewModel.getAnalyzedRecipe(
                    searchRecipeViewModel.allRecipes.value?.results?.get(position)?.id?:0
            )
            searchRecipeViewModel.getIngredientsWithAmount(
                    searchRecipeViewModel.allRecipes.value?.results?.get(position)?.id?:0
            )
            holder.itemView.findNavController().navigate(R.id.action_fragment_search_recipes_to_fragment_search_recipes_details)
        }
    }

    override fun getItemCount(): Int {
        return searchRecipeViewModel.allRecipes.value?.results?.size ?: 0
    }
}
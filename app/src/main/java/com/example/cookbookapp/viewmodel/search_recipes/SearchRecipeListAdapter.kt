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

class SearchRecipeListAdapter (var searchRecipeViewModel: SearchRecipeViewModel):
    RecyclerView.Adapter<SearchRecipeListAdapter.SearchRecipeHolder>()
{
    inner class SearchRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_search_recipes_row,
            parent,
            false
        )
        return SearchRecipeHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecipeHolder, position: Int) {
        val textViewRecipeSearchTitle = holder.itemView.findViewById<TextView>(R.id.recipeSearchTitle)
        val textViewRecipeSearchReadyInMinutes = holder.itemView.findViewById<TextView>(R.id.recipeSearchReadyInMinutes)
        val textViewRecipeSearchServings = holder.itemView.findViewById<TextView>(R.id.recipeSearchServings)
        val imageViewRecipeSearch = holder.itemView.findViewById<ImageView>(R.id.recipeSearchImage)

        textViewRecipeSearchTitle.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.title
        textViewRecipeSearchReadyInMinutes.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.readyInMinutes?.toString() + " min"
        textViewRecipeSearchServings.text = searchRecipeViewModel.allRecipes.value?.results?.get(position)?.servings?.toString()

        val joinedImageURL = searchRecipeViewModel.allRecipes.value?.baseUri + searchRecipeViewModel.allRecipes.value?.results?.get(position)?.image
        Glide.with(imageViewRecipeSearch.context)
                .asBitmap()
                .load(joinedImageURL)
                .into(imageViewRecipeSearch)


        holder.itemView.setOnClickListener {
//            searchRecipeViewModel.allRecipes.value?.results?.get(position)?.id?.let { it1 ->
//                searchRecipeViewModel.setNewImageUrl(
//                        searchRecipeViewModel.allRecipes.value?.baseUri +
//                        searchRecipeViewModel.allRecipes.value?.results?.get(position)?.image
//                )
//                searchRecipeViewModel.allRecipes.value?.results?.get(position)?.title?.let { it2 ->
//                    searchRecipeViewModel.setNewTitle(
//                        it2
//                    )
//                }
//                searchRecipeViewModel.getAnalyzedRecipe(it1)
//                searchRecipeViewModel.getIngredientsWithAmount(it1)
//            }
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
package com.example.cookbookapp.viewmodel.search_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R
import com.example.cookbookapp.model.room.entities.FavouriteRecipe
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val headerItemViewType = 1
private const val listItemViewType = 2
private const val urlNotFound = "Recipe under given Url not found"

class SearchRecipeDetailsListAdapter (private val searchRecipeViewModel: SearchRecipeViewModel,
                                      private val favouriteRecipesViewModel: FavouriteRecipesViewModel):
    RecyclerView.Adapter<SearchRecipeDetailsListAdapter.SearchRecipeHolder>()
{
    inner class SearchRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeHolder {
        return if(viewType == headerItemViewType)
        {
            SearchRecipeHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.recipe_details_row_header,
                    parent,
                    false
                )
            )
        }
        else
        {
            SearchRecipeHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.recipe_details_row,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SearchRecipeHolder, position: Int) {
        if(getItemViewType(position) == headerItemViewType &&
                searchRecipeViewModel.recipeIngredientWithAmounts.value?.extendedIngredients?.size != 0)
        {
            val image = holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(image.context)
                    .asBitmap()
                    .placeholder(R.drawable.no_image_available)
                    .load(searchRecipeViewModel.allRecipes.value?.baseUri +
                            searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.image)
                    .into(image)

            val title = holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            title.text = searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.title

            val ingredients = holder.itemView.findViewById<TextView>(R.id.recipeSearchHeader)
            ingredients.text = createIngredientsList()

            val favouriteButton = holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
            favouriteButton.setOnClickListener {
                favouriteRecipesViewModel.addFavouriteRecipe(
                    FavouriteRecipe(
                        searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.title?:"",
                        searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.readyInMinutes?:0,
                        searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.servings?:0,
                        searchRecipeViewModel.allRecipes.value?.baseUri +
                                searchRecipeViewModel.allRecipes.value?.results?.get(searchRecipeViewModel.currentIndex)?.image,
                        searchRecipeViewModel.recipeIngredientWithAmounts.value?.extendedIngredients?: emptyList(),
                        searchRecipeViewModel.steps.value?: emptyList()
                    )
                )
                favouriteButton.setImageResource(R.drawable.ic_favorite_24px)
            }
        }
        else if(searchRecipeViewModel.steps.value?.size != 0)
        {
            val stepNumber = holder.itemView.findViewById<TextView>(R.id.recipeSearchNumber)
            val stepInstruction = holder.itemView.findViewById<TextView>(R.id.recipeSearchStep)
            val stepEquipment = holder.itemView.findViewById<TextView>(R.id.recipeSearchEquipment)
            val stepIngredients = holder.itemView.findViewById<TextView>(R.id.recipeSearchIngredients)

            stepNumber.text = searchRecipeViewModel.steps.value?.get(position - 1)?.number.toString()
            stepInstruction.text = searchRecipeViewModel.steps.value?.get(position - 1)?.step.toString()
            stepEquipment.text = createEquipmentForStepList(position)
            stepIngredients.text = createIngredientsForStepList(position)
        }
        else
        {
            val image = holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(image.context)
                    .asBitmap()
                    .load(R.drawable.no_image_available)
                    .into(image)

            val title = holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            title.text = urlNotFound

            val favouriteButton = holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
            favouriteButton.isEnabled = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0)
        {
            headerItemViewType
        }
        else
        {
            listItemViewType
        }
    }

    private fun createIngredientsList(): StringBuilder
    {
        val ingredientsAmount = StringBuilder()
        ingredientsAmount.append("Ingredients:")
        ingredientsAmount.append("\n")
        for(i in 0 until (searchRecipeViewModel.recipeIngredientWithAmounts.value?.extendedIngredients?.size ?: 0))
        {
            ingredientsAmount.append("\u2022")
            ingredientsAmount.append(" ")
            ingredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
            extendedIngredients?.get(i)?.name)

            ingredientsAmount.append(" ")
            ingredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
            extendedIngredients?.get(i)?.measures?.metric?.amount)

            ingredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
            extendedIngredients?.get(i)?.measures?.metric?.unitShort)

            ingredientsAmount.append("\n")
        }
        return ingredientsAmount
    }

    private fun createEquipmentForStepList(position: Int): StringBuilder
    {
        val equipment = StringBuilder()
        equipment.append("Equipment: ")
        for(i in 0..(searchRecipeViewModel.steps.value?.get(position - 1)?.equipment?.size?.minus(1) ?: 0))
        {
            equipment.append(searchRecipeViewModel.steps.value?.get(position - 1)?.equipment?.get(i)?.name)
            if (searchRecipeViewModel.steps.value?.get(position - 1)?.equipment?.get(i)?.temperature?.number != null)
            {
                equipment.append(" ")
            }
            equipment.append(searchRecipeViewModel.steps.value?.get(position - 1)?.equipment?.get(i)?.temperature?.number?:"")
            equipment.append(searchRecipeViewModel.steps.value?.get(position - 1)?.equipment?.get(i)?.temperature?.unit?:"").append(", ")
        }
        return equipment
    }

    private fun createIngredientsForStepList(position: Int): StringBuilder
    {
        val ingredients = StringBuilder()
        ingredients.append("Ingredients: ")
        for(i in 0..(searchRecipeViewModel.steps.value?.get(position - 1)?.ingredients?.size?.minus(1) ?: 0))
        {
            ingredients.append(searchRecipeViewModel.steps.value?.get(position - 1)?.ingredients?.get(i)?.name).append(", ")
        }
        return ingredients
    }

    override fun getItemCount(): Int {
        return searchRecipeViewModel.steps.value?.size?.plus(1) ?:0
    }
}
package com.example.cookbookapp.viewmodel.extract_recipe

import android.util.Log
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
private const val imageNotFound = "Image not found"

class ExtractRecipeListAdapter ( val extractRecipeViewModel: ExtractRecipeViewModel,
                                 val favouriteRecipesViewModel: FavouriteRecipesViewModel):
    RecyclerView.Adapter<ExtractRecipeListAdapter.ExtractRecipeHolder>()
{
    inner class ExtractRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtractRecipeHolder {
        return if(viewType == headerItemViewType)
        {
            ExtractRecipeHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_details_row_header,
                parent,
                false
            )
            )
        }
        else
        {
            ExtractRecipeHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_details_row,
                parent,
                false
            )
            )
        }
    }

    override fun onBindViewHolder(holder: ExtractRecipeHolder, position: Int) {
        if(getItemViewType(position) == headerItemViewType &&
                extractRecipeViewModel.extractedRecipe.value?.analyzedInstructions?.size ?:0  != 0)
        {
            val tmpIngredientsAmount = StringBuilder()
            tmpIngredientsAmount.append("Ingredients:")
            tmpIngredientsAmount.append("\n")
            for(i in 0..(extractRecipeViewModel.extractedRecipe.value?.extendedIngredients?.size ?: 0) -1)
            {
                tmpIngredientsAmount.append("\u2022")
                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(
                    extractRecipeViewModel.extractedRecipe.value?.extendedIngredients?.get(i)?.name)

                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(
                    extractRecipeViewModel.extractedRecipe.value?.extendedIngredients?.get(i)?.measures?.metric?.amount)

                tmpIngredientsAmount.append(
                    extractRecipeViewModel.extractedRecipe.value?.extendedIngredients?.get(i)?.measures?.metric?.unitShort)

                tmpIngredientsAmount.append("\n")
            }
            val titleRecipeSearchHeader =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = extractRecipeViewModel.extractedRecipe.value?.title

            val textViewRecipeSearchHeader =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchHeader)
            textViewRecipeSearchHeader.text = tmpIngredientsAmount

            val imageViewRecipeSearchHeader =
                holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(imageViewRecipeSearchHeader.context)
                    .asBitmap()
                    .placeholder(R.drawable.no_image_available)
                    .load(extractRecipeViewModel.extractedRecipe.value?.image)
                    .into(imageViewRecipeSearchHeader)

            val floatingButtonRecipeSearch =
                holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
            floatingButtonRecipeSearch.setOnClickListener {
                favouriteRecipesViewModel.addFavouriteRecipe(
                    FavouriteRecipe(
                    extractRecipeViewModel.extractedRecipe.value?.title?:"",
                    extractRecipeViewModel.extractedRecipe.value?.readyInMinutes?:0,
                    extractRecipeViewModel.extractedRecipe.value?.servings?:0,
                    extractRecipeViewModel.extractedRecipe.value?.image?:"",
                    extractRecipeViewModel.extractedRecipe.value?.extendedIngredients?: emptyList(),
                    extractRecipeViewModel.steps.value ?: emptyList()
                    )
                )
                floatingButtonRecipeSearch.setImageResource(R.drawable.ic_favorite_24px)
            }
        }
        else if(extractRecipeViewModel.extractedRecipe.value?.analyzedInstructions?.size ?:0 != 0)
        {
            val textViewRecipeSearchNumber =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchNumber)
            val textViewRecipeSearchStep =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchStep)
            val textViewRecipeSearchEquipment =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchEquipment)
            val textViewRecipeSearchIngredients =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchIngredients)

            textViewRecipeSearchNumber.text =
                extractRecipeViewModel.steps.value?.get(position - 1)?.number.toString()
            textViewRecipeSearchStep.text =
                extractRecipeViewModel.steps.value?.get(position - 1)?.step

            val tmpEquipment = StringBuilder()
            tmpEquipment.append("Equipment: ")
            for(i in 0..(extractRecipeViewModel.steps.value?.get(position - 1)?.equipment?.size?.minus(1) ?: 0))
            {
                tmpEquipment.append(extractRecipeViewModel.steps.value?.get(position - 1)?.equipment?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchEquipment.text = tmpEquipment

            val tmpIngredients = StringBuilder()
            tmpIngredients.append("Ingredients: ")
            for(i in 0..(extractRecipeViewModel.steps.value?.get(position - 1)?.ingredients?.size?.minus(1) ?: 0))
            {
                tmpIngredients.append(extractRecipeViewModel.steps.value?.get(position - 1)?.ingredients?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchIngredients.text = tmpIngredients
        }
        else
        {
            val imageViewRecipeSearchHeader =
                    holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            imageViewRecipeSearchHeader.setImageResource(R.drawable.no_image_available)

            val titleRecipeSearchHeader =
                    holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = urlNotFound

            val favouriteButton =
                    holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
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

    override fun getItemCount(): Int {
        return extractRecipeViewModel.steps.value?.size?.plus(1) ?:0
    }
}
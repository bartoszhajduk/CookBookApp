package com.example.cookbookapp.viewmodel.favourite_recipes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R
import com.example.cookbookapp.model.room.entities.FavouriteRecipe
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val headerItemViewType = 1
private const val listItemViewType = 2
private const val urlNotFound = "Recipe under given Url not found"
private const val imageNotFound = "Image not found"

class FavouriteRecipesDetailsListAdapter (private val favouriteRecipesViewModel: FavouriteRecipesViewModel,
                                          private val favouriteRecipe: LiveData<FavouriteRecipe>):
        RecyclerView.Adapter<FavouriteRecipesDetailsListAdapter.FavouriteRecipeHolder>()
{
    inner class FavouriteRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipeHolder {
        return if(viewType == headerItemViewType)
        {
            FavouriteRecipeHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.recipe_details_row_header,
                    parent,
                    false
            )
            )
        }
        else
        {
            FavouriteRecipeHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.recipe_details_row,
                    parent,
                    false
            )
            )
        }
    }

    override fun onBindViewHolder(holder: FavouriteRecipeHolder, position: Int) {
        if(getItemViewType(position) == headerItemViewType &&
                favouriteRecipe.value?.extendedIngredients?.size != 0)
        {
            val tmpIngredientsAmount = StringBuilder()
            tmpIngredientsAmount.append("Ingredients:")
            tmpIngredientsAmount.append("\n")
            for(i in 0..(favouriteRecipe.value?.extendedIngredients?.size ?: 0) -1)
            {
                tmpIngredientsAmount.append("\u2022")
                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(
                        favouriteRecipe.value?.extendedIngredients?.get(i)?.name)

                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(
                        favouriteRecipe.value?.extendedIngredients?.get(i)?.measures?.metric?.amount)

                tmpIngredientsAmount.append(
                        favouriteRecipe.value?.extendedIngredients?.get(i)?.measures?.metric?.unitShort)

                tmpIngredientsAmount.append("\n")
            }
            val ingredientsWithAmountRecipeSearchHeader =
                    holder.itemView.findViewById<TextView>(R.id.recipeSearchHeader)
            ingredientsWithAmountRecipeSearchHeader.text = tmpIngredientsAmount

            val titleRecipeSearchHeader =
                    holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = favouriteRecipe.value?.title

            val imageViewRecipeSearchHeader =
                    holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)

            Glide.with(imageViewRecipeSearchHeader.context)
                    .asBitmap()
                    .placeholder(R.drawable.no_image_available)
                    .load(favouriteRecipe.value?.image)
                    .into(imageViewRecipeSearchHeader)

            val floatingButtonRecipeSearch =
                    holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
            floatingButtonRecipeSearch.setImageResource(R.drawable.ic_favorite_24px)

            floatingButtonRecipeSearch.setOnClickListener {
                favouriteRecipe.value?.let { favouriteRecipe ->
                    favouriteRecipesViewModel.deleteFavouriteRecipe(favouriteRecipe)
                    floatingButtonRecipeSearch.setImageResource(R.drawable.ic_favorite_border_24px)
                }
            }
        }
        else if(favouriteRecipe.value?.steps?.size != 0)
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
                    favouriteRecipe.value?.steps?.get(position - 1)?.number.toString()
            textViewRecipeSearchStep.text =
                    favouriteRecipe.value?.steps?.get(position - 1)?.step

            val tmpEquipment = StringBuilder()
            tmpEquipment.append("Equipment: ")
            for(i in 0..(favouriteRecipe.value?.steps?.get(position - 1)?.equipment?.size?.minus(1) ?: 0))
            {
                tmpEquipment.append(favouriteRecipe.value?.steps?.get(position - 1)?.equipment?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchEquipment.text = tmpEquipment

            val tmpIngredients = StringBuilder()
            tmpIngredients.append("Ingredients: ")
            for(i in 0..(favouriteRecipe.value?.steps?.get(position - 1)?.ingredients?.size?.minus(1) ?: 0 ))
            {
                tmpIngredients.append(favouriteRecipe.value?.steps?.get(position - 1)?.ingredients?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchIngredients.text = tmpIngredients
        }
        else
        {
            val imageViewRecipeSearchHeader =
                    holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(imageViewRecipeSearchHeader.context)
                    .asBitmap()
                    .placeholder(R.drawable.no_image_available)
                    .load(R.drawable.no_image_available)
                    .into(imageViewRecipeSearchHeader)

            val titleRecipeSearchHeader =
                    holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = urlNotFound
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
        return favouriteRecipe.value?.steps?.size?.plus(1) ?:0
    }
}
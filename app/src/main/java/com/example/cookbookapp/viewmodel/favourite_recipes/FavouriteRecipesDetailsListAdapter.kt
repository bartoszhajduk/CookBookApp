package com.example.cookbookapp.viewmodel.favourite_recipes

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
            for(i in 0 until (favouriteRecipe.value?.extendedIngredients?.size ?: 0))
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

            val image = holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(image.context)
                    .asBitmap()
                    .placeholder(R.drawable.no_image_available)
                    .load(favouriteRecipe.value?.image)
                    .into(image)

            val title = holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            title.text = favouriteRecipe.value?.title

            val ingredients = holder.itemView.findViewById<TextView>(R.id.recipeSearchHeader)
            ingredients.text = tmpIngredientsAmount

            val favouriteButton = holder.itemView.findViewById<FloatingActionButton>(R.id.recipeSearchFavouriteButton)
            favouriteButton.setImageResource(R.drawable.ic_favorite_24px)

            favouriteButton.setOnClickListener {
                favouriteRecipe.value?.let { favouriteRecipe ->
                    favouriteRecipesViewModel.deleteFavouriteRecipe(favouriteRecipe)
                    favouriteButton.setImageResource(R.drawable.ic_favorite_border_24px)
                }
            }
        }
        else if(favouriteRecipe.value?.steps?.size != 0)
        {
            val stepNumber = holder.itemView.findViewById<TextView>(R.id.recipeSearchNumber)
            val stepInstruction = holder.itemView.findViewById<TextView>(R.id.recipeSearchStep)
            val stepEquipment = holder.itemView.findViewById<TextView>(R.id.recipeSearchEquipment)
            val stepIngredients = holder.itemView.findViewById<TextView>(R.id.recipeSearchIngredients)

            stepNumber.text = favouriteRecipe.value?.steps?.get(position - 1)?.number.toString()
            stepInstruction.text = favouriteRecipe.value?.steps?.get(position - 1)?.step

            val tmpEquipment = StringBuilder()
            tmpEquipment.append("Equipment: ")
            for(i in 0..(favouriteRecipe.value?.steps?.get(position - 1)?.equipment?.size?.minus(1) ?: 0))
            {
                tmpEquipment.append(favouriteRecipe.value?.steps?.get(position - 1)?.equipment?.get(i)?.name).append(", ")
            }
            stepEquipment.text = tmpEquipment

            val tmpIngredients = StringBuilder()
            tmpIngredients.append("Ingredients: ")
            for(i in 0..(favouriteRecipe.value?.steps?.get(position - 1)?.ingredients?.size?.minus(1) ?: 0 ))
            {
                tmpIngredients.append(favouriteRecipe.value?.steps?.get(position - 1)?.ingredients?.get(i)?.name).append(", ")
            }
            stepIngredients.text = tmpIngredients
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

    override fun getItemCount(): Int {
        return favouriteRecipe.value?.steps?.size?.plus(1) ?:0
    }
}
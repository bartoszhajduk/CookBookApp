package com.example.cookbookapp.viewmodel.search_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R
import org.w3c.dom.Text

private val headerItemViewType = 1
private val listItemViewType = 2

class SearchRecipeDetailsListAdapter (var searchRecipeViewModel: SearchRecipeViewModel):
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

        if(getItemViewType(position) == headerItemViewType)
        {
            val tmpIngredientsAmount = StringBuilder()
            tmpIngredientsAmount.append("Ingredients:")
            tmpIngredientsAmount.append("\n")
            for(i in 0..(searchRecipeViewModel.recipeIngredientWithAmounts.value?.extendedIngredients?.size ?: 0) -1)
            {
                tmpIngredientsAmount.append("\u2022")
                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
                    extendedIngredients?.get(i)?.name)

                tmpIngredientsAmount.append(" ")
                tmpIngredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
                    extendedIngredients?.get(i)?.measures?.metric?.amount)

                tmpIngredientsAmount.append(searchRecipeViewModel.recipeIngredientWithAmounts.value?.
                    extendedIngredients?.get(i)?.measures?.metric?.unitShort)

                tmpIngredientsAmount.append("\n")
            }
            val titleRecipeSearchHeader =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = searchRecipeViewModel.currentTitle
            val textViewRecipeSearchHeader =
                holder.itemView.findViewById<TextView>(R.id.recipeSearchHeader)
            textViewRecipeSearchHeader.text = tmpIngredientsAmount
            val imageViewRecipeSearchHeader =
                holder.itemView.findViewById<ImageView>(R.id.recipeSearchHeaderImage)
            Glide.with(imageViewRecipeSearchHeader.context)
                    .asBitmap()
                    .load(searchRecipeViewModel.currentImageUrl)
                    .into(imageViewRecipeSearchHeader)
        }
        else
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
                searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.number.toString()
            textViewRecipeSearchStep.text =
                searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.step.toString()

            val tmpEquipment = StringBuilder()
            tmpEquipment.append("Equipment: ")
            for(i in 0..(searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.equipment?.size?.minus(1) ?: 0))
            {
                tmpEquipment.append(searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.equipment?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchEquipment.text = tmpEquipment

            val tmpIngredients = StringBuilder()
            tmpIngredients.append("Ingredients: ")
            for(i in 0..(searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.ingredients?.size?.minus(1) ?: 0))
            {
                tmpIngredients.append(searchRecipeViewModel.recipeDetails.value?.get(position - 1)?.ingredients?.get(i)?.name).append(", ")
            }
            textViewRecipeSearchIngredients.text = tmpIngredients
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
        return searchRecipeViewModel.recipeDetails.value?.size?.plus(1) ?:0
    }
}
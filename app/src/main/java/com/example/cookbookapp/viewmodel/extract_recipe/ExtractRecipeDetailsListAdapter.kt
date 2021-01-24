package com.example.cookbookapp.viewmodel.extract_recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbookapp.R

private val headerItemViewType = 1
private val listItemViewType = 2
private val notFound = "Recipe under given Url not found"

class ExtractRecipeListAdapter ( var extractRecipeViewModel: ExtractRecipeViewModel ):
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
                .load(extractRecipeViewModel.extractedRecipe.value?.image)
                .into(imageViewRecipeSearchHeader)
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
            Glide.with(imageViewRecipeSearchHeader.context)
                    .asBitmap()
                    .load(R.drawable.no_image_available)
                    .into(imageViewRecipeSearchHeader)

            val titleRecipeSearchHeader =
                    holder.itemView.findViewById<TextView>(R.id.recipeSearchHeaderTitle)
            titleRecipeSearchHeader.text = notFound
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
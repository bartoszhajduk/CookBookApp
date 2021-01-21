package com.example.cookbookapp.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookapp.R
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.IngredientsWithAmount
import com.example.cookbookapp.model.entities.GetAnalyzedRecipe.Step

class SearchRecipeDetailsListAdapter (var data: LiveData<List<Step>>, var ingredientsWithAmount: LiveData<IngredientsWithAmount>):
    RecyclerView.Adapter<SearchRecipeDetailsListAdapter.SearchRecipeHolder>()
{
    inner class SearchRecipeHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_search_recipes_details_row,
            parent,
            false
        )
        return SearchRecipeHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecipeHolder, position: Int) {
        val textViewRecipeSearchNumber = holder.itemView.findViewById<TextView>(R.id.recipeSearchNumber)
        val textViewRecipeSearchStep = holder.itemView.findViewById<TextView>(R.id.recipeSearchStep)
        val textViewRecipeSearchEquipment = holder.itemView.findViewById<TextView>(R.id.recipeSearchEquipment)
        val textViewRecipeSearchIngredients = holder.itemView.findViewById<TextView>(R.id.recipeSearchIngredients)

        textViewRecipeSearchNumber.text = data.value?.get(position)?.number.toString()
        textViewRecipeSearchStep.text = data.value?.get(position)?.step.toString()

        val tmpEquipment = StringBuilder()
        tmpEquipment.append("Equipment: ")
        for(i in 0..(data.value?.get(position)?.equipment?.size?.minus(1) ?: 0))
        {
            tmpEquipment.append(data.value?.get(position)?.equipment?.get(i)?.name).append(", ")
        }
        textViewRecipeSearchEquipment.text = tmpEquipment


        val tmpIngredients = StringBuilder()
        tmpIngredients.append("Ingredients: ")
        for(i in 0..(data.value?.get(position)?.ingredients?.size?.minus(1) ?: 0))
        {
            tmpIngredients.append(data.value?.get(position)?.ingredients?.get(i)?.name).append(", ")
        }
        for(k in 0..(ingredientsWithAmount.value?.extendedIngredients?.size ?: 0) - 1)
        {
            println(  ingredientsWithAmount.value?.extendedIngredients?.get(k)?.id.toString() +
                    ingredientsWithAmount.value?.extendedIngredients?.get(k)?.name +
                    ingredientsWithAmount.value?.extendedIngredients?.get(k)?.measures?.metric?.amount +
                    ingredientsWithAmount.value?.extendedIngredients?.get(k)?.measures?.metric?.unitShort)
        }
        textViewRecipeSearchIngredients.text = tmpIngredients
//            tmpIngredients.append((data.value?.get(position)?.ingredients?.get(i)?.name ?: ""))
//            for(j in 0..((ingredientsWithAmount.value?.ingredients?.size ?: 0) - 1))
//            {
//                if(data.value?.get(position)?.ingredients?.get(i)?.name.equals(ingredientsWithAmount.value?.ingredients?.get(j)?.name))
//                {
//                    tmpIngredients.append(" ")
//                    tmpIngredients.append(ingredientsWithAmount.value?.ingredients?.get(j)?.amount?.metric?.value)
//                    tmpIngredients.append(ingredientsWithAmount.value?.ingredients?.get(j)?.amount?.metric?.unit)
//                }
//            }
//            tmpIngredients.append(", ")
//        }
//        textViewRecipeSearchIngredients.text = tmpIngredients
    }

    override fun getItemCount(): Int {
        return data.value?.size ?:0
    }
}
package com.example.cookbookapp.ui.extract_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.extract_recipe.ExtractRecipeViewModel
import kotlinx.android.synthetic.main.fragment_extract_recipe.*

class ExtractRecipeFragment : Fragment() {
    private lateinit var extractRecipeViewModel: ExtractRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        extractRecipeViewModel = ViewModelProvider(requireActivity()).get(ExtractRecipeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_extract_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_extract_recipe.setOnClickListener {
            extractRecipeViewModel.setUrl(editTextExtractRecipe.text.toString())
            extractRecipeViewModel.getExtractedRecipe()
            findNavController().navigate(R.id.action_fragment_extract_recipe_to_fragment_extract_recipe_details)
        }
    }
}
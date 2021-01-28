package com.example.cookbookapp.ui.search_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesViewModel
import com.example.cookbookapp.viewmodel.search_recipes.SearchRecipeDetailsListAdapter
import com.example.cookbookapp.viewmodel.search_recipes.SearchRecipeViewModel
import kotlinx.android.synthetic.main.fragment_search_recipes_details.*

class SearchRecipeDetailsFragment : Fragment() {
    private lateinit var searchRecipeViewModel: SearchRecipeViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchRecipeDetailsListAdapter: SearchRecipeDetailsListAdapter
    private lateinit var favouriteRecipesViewModel: FavouriteRecipesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        searchRecipeViewModel = ViewModelProvider(requireActivity()).get(SearchRecipeViewModel::class.java)
        favouriteRecipesViewModel = ViewModelProvider(requireActivity()).get(FavouriteRecipesViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        searchRecipeDetailsListAdapter = SearchRecipeDetailsListAdapter(searchRecipeViewModel, favouriteRecipesViewModel)

        searchRecipeViewModel.steps.observe(viewLifecycleOwner, {
            searchRecipeDetailsListAdapter.notifyDataSetChanged()
        })
        searchRecipeViewModel.recipeIngredientWithAmounts.observe(viewLifecycleOwner, {
            searchRecipeDetailsListAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_search_recipes_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecipeDetailsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = searchRecipeDetailsListAdapter
        }
    }
}
package com.example.cookbookapp.ui.search_recipe_details_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.SearchRecipeDetailsListAdapter
import com.example.cookbookapp.viewmodel.SearchRecipeListAdapter
import com.example.cookbookapp.viewmodel.SearchRecipeViewModel
import kotlinx.android.synthetic.main.fragment_search_recipes.*
import kotlinx.android.synthetic.main.fragment_search_recipes_details.*

class SearchRecipeDetailsFragment : Fragment() {
    private lateinit var searchRecipeViewModel: SearchRecipeViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchRecipeDetailsListAdapter: SearchRecipeDetailsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        searchRecipeViewModel = ViewModelProvider(requireActivity()).get(SearchRecipeViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        searchRecipeDetailsListAdapter = SearchRecipeDetailsListAdapter(searchRecipeViewModel.recipeDetails, searchRecipeViewModel.recipeIngredientWithAmounts)

        searchRecipeViewModel.recipeDetails.observe(viewLifecycleOwner, {
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
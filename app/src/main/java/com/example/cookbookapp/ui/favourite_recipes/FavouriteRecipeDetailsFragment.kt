package com.example.cookbookapp.ui.favourite_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesDetailsListAdapter
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesViewModel
import com.example.cookbookapp.viewmodel.search_recipes.SearchRecipeDetailsListAdapter
import com.example.cookbookapp.viewmodel.search_recipes.SearchRecipeViewModel
import kotlinx.android.synthetic.main.fragment_favourite_recipes_details.*
import kotlinx.android.synthetic.main.fragment_search_recipes_details.*

class FavouriteRecipeDetailsFragment : Fragment() {
    private lateinit var favouriteRecipeDetailsViewModel: FavouriteRecipesViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var favouriteRecipeDetailsListAdapter: FavouriteRecipesDetailsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        favouriteRecipeDetailsViewModel = ViewModelProvider(requireActivity()).get(FavouriteRecipesViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        favouriteRecipeDetailsListAdapter = FavouriteRecipesDetailsListAdapter (
                                           favouriteRecipeDetailsViewModel,
                                           favouriteRecipeDetailsViewModel.currentFavouriteRecipe
                                            )

        favouriteRecipeDetailsViewModel.currentFavouriteRecipe.observe(viewLifecycleOwner, {
            favouriteRecipeDetailsListAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_favourite_recipes_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteRecipeDetailsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = favouriteRecipeDetailsListAdapter
        }
    }
}
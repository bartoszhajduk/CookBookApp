package com.example.cookbookapp.ui.favourite_recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesListAdapter
import com.example.cookbookapp.viewmodel.favourite_recipes.FavouriteRecipesViewModel
import kotlinx.android.synthetic.main.fragment_favourite_recipes.*

class FavouriteRecipesFragment : Fragment() {

    private lateinit var favouriteRecipesViewModel: FavouriteRecipesViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var favouriteRecipesListAdapter: FavouriteRecipesListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favouriteRecipesViewModel = ViewModelProvider(requireActivity()).get(FavouriteRecipesViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        favouriteRecipesListAdapter = FavouriteRecipesListAdapter(favouriteRecipesViewModel)

        favouriteRecipesViewModel.favouriteRecipes.observe(viewLifecycleOwner, {
            favouriteRecipesListAdapter.notifyDataSetChanged()
        })
        return inflater.inflate(R.layout.fragment_favourite_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteRecipesRecyclerView.apply {
            adapter = favouriteRecipesListAdapter
            layoutManager = linearLayoutManager
        }
    }
}
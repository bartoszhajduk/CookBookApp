package com.example.cookbookapp.ui.search_recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.SearchRecipeListAdapter
import com.example.cookbookapp.viewmodel.SearchRecipeViewModel
import kotlinx.android.synthetic.main.fragment_search_recipes.*

class SearchRecipeFragment : Fragment() {
    private lateinit var searchRecipeViewModel: SearchRecipeViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var searchRecipeListAdapter: SearchRecipeListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchRecipeViewModel = ViewModelProvider(requireActivity()).get(SearchRecipeViewModel::class.java)

        gridLayoutManager = GridLayoutManager(requireContext(),2)
        searchRecipeListAdapter = SearchRecipeListAdapter(searchRecipeViewModel)

        searchRecipeViewModel.allRecipes.observe(viewLifecycleOwner, {
            searchRecipeListAdapter.notifyDataSetChanged()
        })
        val root = inflater.inflate(R.layout.fragment_search_recipes, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecipeRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = searchRecipeListAdapter
        }

        buttonSearchRecipes.setOnClickListener {
            searchRecipeViewModel.getAllRecipesByKeyword(editTextSearchRecipes.text.toString())
        }
    }
}
package com.example.cookbookapp.ui.extract_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbookapp.R
import com.example.cookbookapp.viewmodel.extract_recipe.ExtractRecipeListAdapter
import com.example.cookbookapp.viewmodel.extract_recipe.ExtractRecipeViewModel
import kotlinx.android.synthetic.main.fragment_extract_recipe_details.*

class ExtractRecipeDetailsFragment : Fragment() {
    private lateinit var extractRecipeViewModel: ExtractRecipeViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var extractRecipeDetailsListAdapter: ExtractRecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        extractRecipeViewModel = ViewModelProvider(requireActivity()).get(ExtractRecipeViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(requireContext())
        extractRecipeDetailsListAdapter = ExtractRecipeListAdapter(extractRecipeViewModel)

        return inflater.inflate(R.layout.fragment_extract_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        extractRecipeDetailsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = extractRecipeDetailsListAdapter
        }

        extractRecipeViewModel.steps.observe(viewLifecycleOwner, {
            extractRecipeDetailsListAdapter.notifyDataSetChanged()
        })
        super.onViewCreated(view, savedInstanceState)
    }

}
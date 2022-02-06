package com.example.foody.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.adapters.IngredientsAdapter
import com.example.foody.databinding.FragmentIngredientsBinding
import com.example.foody.data.models.ExtendedIngredient
import com.example.foody.data.models.Result

class Ingredients : Fragment() {

    //................values
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val myData: Result? = args?.getParcelable("recipeBundle")


        val IngredientsList: List<ExtendedIngredient>? = myData?.extendedIngredients

        //set the RC
        val recyclerView = binding.ingredientsRc
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        IngredientsList?.let { adapter.setData(it) }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.foody.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.adapters.FavoriteRecipesAdapter
import com.example.foody.adapters.RecipesAdapter
import com.example.foody.databinding.FragmentFavoriteBinding
import com.example.foody.viewModel.MainViewModel
import kotlinx.coroutines.launch


class favoriteFragment : Fragment() {

    //...........init the viewModel
    private val mainViewModel: MainViewModel by viewModels()

    //.........................values
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    //private val mAdapter by lazy { FavoriteRecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

       /* val rc = binding.recyclerView

        rc.adapter = mAdapter
        rc.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, { data ->
                mAdapter.setData(data[0])
            })
        }*/

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
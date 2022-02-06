package com.example.foody.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.R
import com.example.foody.viewModel.MainViewModel
import com.example.foody.adapters.RecipesAdapter
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.data.util.NetworkResult
import com.example.foody.viewModel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var recipesViewModel : RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        val view = binding.root

        setHasOptionsMenu(true)

        setupRecyclerView()
        readDatabase()


        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_recipesFragment_to_blankFragmentRecipesBottomSheet)
        }

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    // ? connecting the adapter from the class RecipesAdapter to the RecyclerView in the layout
    private fun setupRecyclerView() {
        binding.RecyclerView.adapter = mAdapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }
    private fun readDatabase() {
        Log.d("test", "db")
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && recipesViewModel.backToBottomSheet) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                    recipesViewModel.backToBottomSheet = true
                }
            })
        }
    }
    private fun requestApiData() {
        Log.d("test", "api")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun searchApi(searchEntry: String){
        Log.d("test", "net")
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchEntry))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchApi(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, {database->
                if (database.isEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()}
            })
        }
    }
    private fun showShimmerEffect() {
        binding.RecyclerView.showShimmer()
    }
    private fun hideShimmerEffect() {
        binding.RecyclerView.hideShimmer()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
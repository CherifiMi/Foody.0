package com.example.foody


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navArgs
import com.example.foody.adapters.Pageradapter
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.databinding.ActivityDetailsBinding
import com.example.foody.ui.Ingredients
import com.example.foody.ui.Instructions
import com.example.foody.ui.Overview
import com.example.foody.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList



@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val mainViewModel: MainViewModel by viewModels()

    private var glowingStar: Boolean = false

    //...........safe args data
    private val args by navArgs<DetailsActivityArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkIfSaved()

        //..save to favorites star btn
        binding.star.setOnClickListener{saveOrDelete()}

        //..back btn
        binding.Back.setOnClickListener{finish()}

        sendDataToTabview()

    }

    private fun checkIfSaved(){
        mainViewModel.readFavoriteRecipes.observe(this, { favoriteEntity ->
            for (savedRecipies in favoriteEntity){
                if (savedRecipies.result == args.currentItem){
                    binding.star.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
                    glowingStar = true
                }else{
                    binding.star.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    glowingStar = false
                }
            }
        }
        )
    }

    private fun saveOrDelete() {
        if (!glowingStar){
            mainViewModel.insertFavoriteRecipes(FavoritesEntity(args.currentItem))
            binding.star.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            showSnackBar("Recipe saved.")
            glowingStar = true
        }else{
            mainViewModel.deleteOneFavorites(FavoritesEntity(args.currentItem))
            showSnackBar("Recipe deleted.")
            binding.star.setColorFilter(ContextCompat.getColor(this, R.color.white))
            glowingStar = false
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun sendDataToTabview() {
        val fragments = ArrayList<Fragment>()
        fragments.add(Overview())
        fragments.add(Ingredients())
        fragments.add(Instructions())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.currentItem)

        val adapter = Pageradapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


}
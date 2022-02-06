package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.databinding.RecipesRowLayoutBinding
import com.example.foody.data.models.FoodRecipe
import com.example.foody.data.models.Result
import com.example.foody.data.util.RecipesDiffUtil
import com.example.foody.ui.RecipesFragmentDirections

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes = emptyList<Result>()

    class ViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // ? binds the layout of the card with the result model

            fun bind(result: Result){
                binding.result=result
                binding.executePendingBindings()
            }

        val background: ConstraintLayout = binding.recipesRowLayout

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentResult = recipes[position]
        holder.bind(currentResult)
        //............when click on the card navigate to details view with the current data :)
        holder.background.setOnClickListener{
            val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(recipes[position])
            holder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return recipes.size
    }
    //

    fun setData(newData : FoodRecipe){
        val recipesDiffUtil =
            RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
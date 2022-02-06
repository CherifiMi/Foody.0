package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.models.Result
import com.example.foody.databinding.RecipesRowLayoutBinding
import com.example.foody.ui.RecipesFragmentDirections

class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.ViewHolder>() {

    private var recipes = emptyList<FavoritesEntity>()

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
        holder.bind(currentResult.result)

        /*holder.background.setOnClickListener{
            val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(recipes[position].result)
            holder.itemView.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData : FavoritesEntity){
        this.recipes = listOf(newData)
        notifyDataSetChanged()
    }

}
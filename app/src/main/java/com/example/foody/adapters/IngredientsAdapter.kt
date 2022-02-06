package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.data.models.ExtendedIngredient
import com.example.foody.data.util.Constants.Companion.IMAGE_URL

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.Holder>() {

    var dataList = emptyList<ExtendedIngredient>()

    class Holder(view: View): RecyclerView.ViewHolder(view){
        val Name: TextView = view.findViewById(R.id.name)
        val Unit: TextView = view.findViewById(R.id.unit)
        val Consistency: TextView = view.findViewById(R.id.consistency)
        val Amount: TextView = view.findViewById(R.id.amount)
        val Image: ImageView = view.findViewById(R.id.ing_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredients_row_layout, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Name.setText(dataList[position].name)
        holder.Unit.setText(dataList[position].unit)
        holder.Consistency.setText(dataList[position].consistency)
        holder.Amount.setText(dataList[position].amount.toString())
        holder.Image.load(IMAGE_URL+dataList[position].image){
            crossfade(600)
            error(R.drawable.nonet)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun setData (ing: List<ExtendedIngredient>){
        this.dataList = ing
        notifyDataSetChanged()
    }
}
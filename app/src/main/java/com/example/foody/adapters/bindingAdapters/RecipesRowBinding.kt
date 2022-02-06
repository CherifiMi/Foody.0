package com.example.foody.adapters.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foody.R
import org.jsoup.Jsoup

class RecipesRowBinding {
    companion object{

        // ? binding adapters for changing a value from the model to a property in the layout views

        @BindingAdapter("summery")
        @JvmStatic
        fun setSummeryText(
            texteView: TextView,
            summery: String
        ){
            texteView.text = Jsoup.parse(summery).text()
        }

        @BindingAdapter("setNumOfLikes")
        @JvmStatic
        fun setNumOfLikes(
            texteView: TextView,
            likes: Int
        ){
            texteView.text = likes.toString()
        }

        @BindingAdapter("setNumOfMins")
        @JvmStatic
        fun setNumOfMins(
            texteView: TextView,
            likes: Int
        ){
            texteView.text = likes.toString()
        }

        @BindingAdapter("setImageFromUrl")
        @JvmStatic
        fun loudimageUrl(imageView: ImageView, url: String){
            imageView.load(url)
            {
                crossfade(600)
                error(R.drawable.nonet)
            }
        }

        @BindingAdapter("isVegan")
        @JvmStatic
        fun isVegan(view: View, vegan:Boolean){
            if (vegan){
                when(view){
                    is TextView->{
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView->{
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }

                }
            }
        }
    }
}
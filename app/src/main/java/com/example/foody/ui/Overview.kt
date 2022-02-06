package com.example.foody.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.foody.R
import com.example.foody.databinding.FragmentOverviewBinding
import com.example.foody.data.models.Result
import org.jsoup.Jsoup


class Overview : Fragment() {

    //................values
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val myData: Result? = args?.getParcelable("recipeBundle")

        val green = ContextCompat.getColor(requireContext(), R.color.green)

        //.....................setting the values from the details page to the views in the Overview Layout :)
        binding.foodImg0.load(myData?.image)
        binding.hartTxt.setText(myData?.aggregateLikes.toString())
        binding.timeNum.setText(myData?.readyInMinutes.toString())
        binding.titleTxt.setText(myData?.title)

        if(myData?.cheap==true){
            binding.cheapImg.setColorFilter(green)
            binding.cheapTxt.setTextColor(green)
        }
        if (myData?.vegan==true){
            binding.veganImg.setColorFilter(green)
            binding.veganTxt.setTextColor(green)
        }
        if (myData?.dairyFree==true){
            binding.dairyImg.setColorFilter(green)
            binding.dairyTxt.setTextColor(green)
        }
        if (myData?.glutenFree==true){
            binding.glutenImg.setColorFilter(green)
            binding.glutenTxt.setTextColor(green)
        }
        if (myData?.vegetarian==true){
            binding.veganImg.setColorFilter(green)
            binding.veganTxt.setTextColor(green)
        }
        if (myData?.veryHealthy==true){
            binding.healthyImg.setColorFilter(green)
            binding.healthyTxt.setTextColor(green)
        }

        binding.decTxt.setText(Jsoup.parse(myData?.summary).text())



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.foody.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foody.databinding.FragmentInstructionsBinding
import com.example.foody.data.models.Result

class Instructions : Fragment() {

    //................values
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val myData: Result? = args?.getParcelable("recipeBundle")


        myData?.sourceUrl?.let { binding.web.loadUrl(it) }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.foody.ui.fragments.overview

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.foody.R
import com.example.foody.bindingAdapters.RecipesRowBinding
import com.example.foody.dataModel.Result
import com.example.foody.databinding.FragmentOverviewBinding
import com.example.foody.util.Constants

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val args = arguments
        val myBundle: Result = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            args?.getParcelable(Constants.RECIPE_RESULT_KEY, Result::class.java)
        } else {
            args?.getParcelable(Constants.RECIPE_RESULT_KEY)
        } as Result

        binding.imageView.load(myBundle.image)
        binding.menuTitleTextView.text = myBundle.title
        binding.likesTextView.text = myBundle.aggregateLikes.toString()
        binding.timeTextView.text = myBundle.readyInMinutes.toString()

        RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)

        updateColors(myBundle.vegetarian, binding.vegetarianTextView, binding.vegetarianImageView)
        updateColors(myBundle.vegan, binding.veganTextView, binding.vegetarianImageView)
        updateColors(myBundle.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.dairyFree, binding.diaryFreeTextView, binding.diaryFreeImageView)
        updateColors(myBundle.veryHealthy, binding.healthyTextView, binding.healthyImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)


        return view
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
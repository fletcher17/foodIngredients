package com.example.foody.ui.fragments.ingredients

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.R
import com.example.foody.adapters.IngredientsAdapter
import com.example.foody.dataModel.Result
import com.example.foody.databinding.FragmentIngredientsBinding
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle = if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            args?.getParcelable(RECIPE_RESULT_KEY, Result::class.java)
        } else {
            args?.getParcelable(RECIPE_RESULT_KEY)
        }

        setupRecyclerView()
        myBundle?.extendedIngredients?.let { ingredientsAdapter.setData(it) }

        return binding.root
    }


    private fun setupRecyclerView() {
        binding.ingredientRv.adapter = ingredientsAdapter
        binding.ingredientRv.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
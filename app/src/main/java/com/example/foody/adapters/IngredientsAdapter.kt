package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.dataModel.ExtendedIngredient
import com.example.foody.databinding.IngredientsRowLayoutBinding
import com.example.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foody.util.RecipesDiffUtil

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(private val bind: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(bind.root) {

        fun bind(binding: ExtendedIngredient) {
            bind.ingredientImageView.load(BASE_IMAGE_URL.plus(binding.image)) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            bind.ingredientName.text = binding.name.capitalize()
            bind.ingredientAmount.text = binding.amount.toString()
            bind.ingredientUnit.text = binding.unit
            bind.ingredientConsistency.text = binding.consistency
            bind.ingredientOriginal.text = binding.original
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val bind = LayoutInflater.from(parent.context)
        return IngredientsViewHolder(IngredientsRowLayoutBinding.inflate(bind,parent, false))
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredientItem = ingredientsList[position]
        holder.bind(ingredientItem)
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
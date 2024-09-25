package com.example.foody.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.adapters.FavoriteRecipesAdapter
import com.example.foody.data.db.entities.FavoritesEntity

class FavoriteRecipesBinding {

    companion object {

//        @BindingAdapter("viewVisibility", requireAll = false)
//        @JvmStatic
//        fun setDataAndViewVisibility(
//            view: View,
//            favoritesEntity: List<FavoritesEntity>?,
//            mAdapter: FavoriteRecipesAdapter
//        ) {
//            if (favoritesEntity?.isNullOrEmpty() == true) {
//                when(view) {
//                    is ImageView -> {
//                        view.visibility = View.VISIBLE
//                    }
//                    is TextView -> {
//                        view.visibility = View.VISIBLE
//                    }
//                    is RecyclerView -> {
//                        view.visibility = View.INVISIBLE
//                    }
//                }
//            } else {
//                when(view) {
//                    is ImageView -> {
//                        view.visibility = View.INVISIBLE
//                    }
//                    is TextView -> {
//                        view.visibility = View.INVISIBLE
//                    }
//                    is RecyclerView -> {
//                        view.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
        @BindingAdapter("viewVisibility", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?
        ) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isInvisible = dataCheck
                }
                else -> {
                    view.isVisible = favoritesEntity.isNullOrEmpty()
                }
            }
        }
    }
}

//mAdapter.setData(newFavoriteRecipe = favoritesEntity)
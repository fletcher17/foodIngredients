package com.example.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foody.R
import com.example.foody.adapters.PageAdapter
import com.example.foody.data.db.entities.FavoritesEntity
import com.example.foody.databinding.ActivityDetailsBinding
import com.example.foody.ui.fragments.ingredients.IngredientsFragment
import com.example.foody.ui.fragments.instructions.InstructionsFragment
import com.example.foody.ui.fragments.overview.OverviewFragment
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.foody.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@Suppress("OPT_IN_USAGE")
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
//        titles.add("Overview")
//        titles.add("Ingredients")
//        titles.add("Instructions")

        titles.apply {
            add("Overview")
            add("Ingredients")
            add("Instructions")
        }


        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PageAdapter(
            resultBundle, fragments, titles, supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.save_to_favorites_menu -> {
                if (recipeSaved) removeFromFavorite(item) else saveToFavorites(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoriteRecipe = FavoritesEntity(0, args.result)
        mainViewModel.insertFavoriteRecipe(favoriteRecipe)
        changeMeuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved")
        recipeSaved = true
    }

    private fun removeFromFavorite(menuItem: MenuItem) {
        val favoriteRecipe = FavoritesEntity(savedRecipeId, args.result)
        mainViewModel.deleteFavoriteRecipe(favoriteRecipe)
        changeMeuItemColor(menuItem, R.color.white)
        showSnackBar("Removed from Favorites")
        recipeSaved = false
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            try {
                for (savedFavoriteRecipe in favoritesEntity) {
                    if (savedFavoriteRecipe.result.id == args.result.id) {
                        changeMeuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedFavoriteRecipe.id
                        recipeSaved = true
                    } else {
                        changeMeuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun changeMeuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Details", "OnDestroy")
    }
}
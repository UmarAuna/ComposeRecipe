package com.recipe.composerecipe.presentation.ui.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipe.composerecipe.presentation.components.RecipeCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // SurfaceViewRecipeList()
                val recipes = viewModel.recipes.value
                LazyColumn {
                    itemsIndexed(
                        items = recipes
                    ) { index, recipe ->
                        RecipeCard(recipe = recipe, onClick = {} )
                    }
                }
            }
        }
    }
}

@Composable
fun SurfaceViewRecipeList() {
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRecipeList() {
    SurfaceViewRecipeList()
}

package com.recipe.composerecipe.presentation.ui.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipe.composerecipe.presentation.components.RecipeCard
import com.recipe.composerecipe.presentation.components.SearchAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // SurfaceViewRecipeList()
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val keyboardController = LocalSoftwareKeyboardController.current
                val selectedCategory = viewModel.selectedCategory.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged, //Higher order Function
                        onExecuteSearch = viewModel::newSearch,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged
                    )
                    // Spacer(modifier = Modifier.padding(10.dp))
                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
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

package com.recipe.composerecipe.presentation.ui.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipe.composerecipe.presentation.BaseApplication
import com.recipe.composerecipe.presentation.components.CircularIndeterminateProgressBar
import com.recipe.composerecipe.presentation.components.RecipeCard
import com.recipe.composerecipe.presentation.components.SearchAppBar
import com.recipe.composerecipe.ui.theme.ComposeRecipeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    @Inject
    lateinit var application: BaseApplication

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ComposeRecipeTheme(
                    darkTheme = application.isDark.value
                ) {
                    // SurfaceViewRecipeList()
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    // val keyboardController = LocalSoftwareKeyboardController.current
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value

                    Column {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged, // Higher order Function
                            onExecuteSearch = viewModel::newSearch,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onToggleTheme = {
                                application.toggleLightTheme()
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background)

                        ) {
                            // Spacer(modifier = Modifier.padding(10.dp))
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
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

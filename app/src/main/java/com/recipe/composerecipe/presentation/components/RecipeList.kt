package com.recipe.composerecipe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.recipe.composerecipe.domain.Recipe
import com.recipe.composerecipe.presentation.ui.recipeList.PAGE_SIZE
import com.recipe.composerecipe.presentation.ui.recipeList.RecipeListEvent
import com.recipe.composerecipe.presentation.ui.recipeList.RecipeListFragmentDirections
import com.recipe.composerecipe.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface
            )

    ) {
        // Spacer(modifier = Modifier.padding(10.dp))
        if (loading && recipes.isEmpty()) {
            CircularIndeterminateProgressBar(isDisplayed = true)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    // additional check to make sure we don't get duplicate entries or duplicate request
                    if (index + 1 >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(RecipeListEvent.NextPageEvent)
                        // viewModel.nextPage()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null) {
                                // val bundle = Bundle()
                                // bundle.putInt("recipeId", recipe.id)
                                // navController.navigate(R.id.viewRecipe, bundle)
                                navController.navigate(RecipeListFragmentDirections.viewRecipe(recipe.id))
                            } else {
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "Recipe Error! ",
                                        actionLabel = "Ok",
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

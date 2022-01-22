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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.recipe.composerecipe.presentation.BaseApplication
import com.recipe.composerecipe.presentation.components.CircularIndeterminateProgressBar
import com.recipe.composerecipe.presentation.components.DefaultSnackbar
import com.recipe.composerecipe.presentation.components.RecipeCard
import com.recipe.composerecipe.presentation.components.SearchAppBar
import com.recipe.composerecipe.ui.theme.ComposeRecipeTheme
import com.recipe.composerecipe.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

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
                    val page = viewModel.page.value
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged, // Higher order Function
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = "Invalid Category: MILK! ",
                                                actionLabel = "Hide",
                                            )
                                        }
                                    } else {
                                        // viewModel.newSearch()
                                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                    }
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                }
                            )
                        },
                        /*bottomBar = {
                            MyBottomBar()
                        },
                        drawerContent = {
                            MyDrawer()
                        },*/
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
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
                                        viewModel.onChangeRecipeScrollPosition(index)
                                        // additional check to make sure we dont get duplicate entries or duplicate request
                                        if (index + 1 >= (page * PAGE_SIZE) && !loading) {
                                            viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                                            // viewModel.nextPage()
                                        }
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }

                            CircularIndeterminateProgressBar(isDisplayed = loading,)
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(
    // navigationController: NavController
) {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.BrokenImage, "Navigation") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search, "Navigation") },
            selected = true,
            onClick = {
            }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountBalanceWallet, "Navigation") },
            selected = false,
            onClick = {
            }
        )
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text(text = "Item1")
        Text(text = "Item2")
        Text(text = "Item3")
        Text(text = "Item4")
        Text(text = "Item5")
    }
}

@Composable
fun SurfaceViewRecipeList() {
}

@Preview(locale = "en", showBackground = true)
/*@Preview(locale = "ru", showBackground = true)
@Preview(locale = "ar", showBackground = true)*/
@Composable
fun HaHa() {
    Column {
        repeat(3) {
            Text(text = "👻 Hello 👻")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRecipeList() {
    SurfaceViewRecipeList()
}

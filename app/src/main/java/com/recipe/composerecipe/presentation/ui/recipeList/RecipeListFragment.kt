package com.recipe.composerecipe.presentation.ui.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.recipe.composerecipe.presentation.BaseApplication
import com.recipe.composerecipe.presentation.components.RecipeList
import com.recipe.composerecipe.presentation.components.SearchAppBar
import com.recipe.composerecipe.presentation.ui.recipeList.RecipeListEvent.NextPageEvent
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
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(NextPageEvent)
                            },
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
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

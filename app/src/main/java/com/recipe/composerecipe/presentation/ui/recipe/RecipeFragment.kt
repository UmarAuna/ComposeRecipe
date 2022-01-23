package com.recipe.composerecipe.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.recipe.composerecipe.presentation.BaseApplication
import com.recipe.composerecipe.presentation.components.RecipeView
import com.recipe.composerecipe.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.recipe.composerecipe.ui.theme.ComposeRecipeTheme
import com.recipe.composerecipe.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val args: RecipeFragmentArgs by navArgs()

    private val viewModel: RecipeViewModel by viewModels()

    private var recideId: MutableState<Int> = mutableStateOf(-1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Main).launch {
            delay(1000)
            arguments?.getInt("recipeId")?.let { rId ->
                // recideId.value = rId
                // recideId.value = args.recipeId
                viewModel.onTriggerEvent(GetRecipeEvent(rId))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()
                ComposeRecipeTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState

                ) {
                    SurfaceViewRecipe()
                }
            }
        }
    }

    @Composable
    fun SurfaceViewRecipe() {
        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (loading && recipe == null) {
                    Text(text = "Loading...")
                } else {
                    recipe?.let {
                        if (it.id == 1) {
                            snackbarController.showSnackbar(
                                scaffoldState = scaffoldState,
                                message = "An error occured with this recipe",
                                actionLabel = "Ok"
                            )
                        } else {
                            RecipeView(recipe = it)
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreviewRecipe() {
        val loading = viewModel.loading.value
        val scaffoldState = rememberScaffoldState()
        ComposeRecipeTheme(
            displayProgressBar = loading,
            scaffoldState = scaffoldState
        ) {
            SurfaceViewRecipe()
        }
    }
}

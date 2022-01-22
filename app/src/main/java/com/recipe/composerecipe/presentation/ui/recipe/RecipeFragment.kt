package com.recipe.composerecipe.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.recipe.composerecipe.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {
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
                SurfaceViewRecipe()
            }
        }
    }

    @Composable
    fun SurfaceViewRecipe() {
        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recipe?.let {
                    "Selected recipeId: ${recipe.title}"
                } ?: "Loading...",
                style = TextStyle(
                    fontSize = 21.sp
                )
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreviewRecipe() {
        SurfaceViewRecipe()
    }
}

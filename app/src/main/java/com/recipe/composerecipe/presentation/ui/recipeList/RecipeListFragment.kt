package com.recipe.composerecipe.presentation.ui.recipeList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.recipe.composerecipe.R
import com.recipe.composerecipe.util.TAG
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
                for (recipe in recipes) {
                    Log.d(TAG, "OnCreaveView: ${recipe.title}")
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Recipe List Fragment",
                        style = TextStyle(
                            fontSize = 21.sp
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = { findNavController().navigate(R.id.viewRecipe) }
                    ) {
                        Text(text = "TO RECIPE FRAGMENT")
                    }
                }
            }
        }
    }
}

@Composable
fun SurfaceViewRecipeList() {
    val navController = rememberNavController()
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Recipe List Fragment",
            style = TextStyle(
                fontSize = 21.sp
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = { navController.navigate(R.id.viewRecipe) }
        ) {
            Text(text = "TO RECIPE FRAGMENT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRecipeList() {
    SurfaceViewRecipeList()
}

package com.recipe.composerecipe.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.recipe.composerecipe.presentation.ui.recipeList.FoodCategory
import com.recipe.composerecipe.presentation.ui.recipeList.getAllFoodCategories


@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search")
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategory = {
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = { onExecuteSearch() },
                    )
                }
            }
        }
    }
}
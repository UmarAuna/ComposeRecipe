package com.recipe.composerecipe.repository

import com.recipe.composerecipe.domain.Recipe

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List<Recipe>
    suspend fun get(token: String, id: Int): Recipe
}

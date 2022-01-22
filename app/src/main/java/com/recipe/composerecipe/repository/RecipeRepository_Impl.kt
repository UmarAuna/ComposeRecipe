package com.recipe.composerecipe.repository

import com.recipe.composerecipe.domain.Recipe
import com.recipe.composerecipe.network.model.RecipeDtoMapper
import com.recipe.composerecipe.network.responses.RetrofitService

class RecipeRepository_Impl(
    private val retrofitService: RetrofitService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(retrofitService.search(token, page, query).recipes)
       /* val result = retrofitService.search(token, page, query).recipes
        return mapper.toDomainList(result)*/
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(retrofitService.get(token, id))
    }
}

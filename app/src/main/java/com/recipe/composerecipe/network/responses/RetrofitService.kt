package com.recipe.composerecipe.network.responses

import com.recipe.composerecipe.network.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): RecipeDto
}

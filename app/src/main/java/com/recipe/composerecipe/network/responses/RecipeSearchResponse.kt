package com.recipe.composerecipe.network.responses

import com.google.gson.annotations.SerializedName
import com.recipe.composerecipe.network.model.RecipeDto

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeDto>,

)

package com.recipe.composerecipe.network.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RecipeDto(
    @SerializedName("pk")
    val pk: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("publisher")
    val publisher: String? = null,
    @SerializedName("featured_image")
    val featuredImage: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("source_url")
    val sourceUrl: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("cooking_instructions")
    val cookingInstructions: String? = null,
    @SerializedName("ingredients")
    val ingredients: List<String>? = null,
    @SerializedName("date_added")
    val dateAdded: String? = null,
    @SerializedName("date_updated")
    val dateUpdated: String? = null,

) : Parcelable

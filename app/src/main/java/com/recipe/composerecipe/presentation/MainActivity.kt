package com.recipe.composerecipe.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.recipe.composerecipe.R
import com.recipe.composerecipe.domain.Recipe
import com.recipe.composerecipe.network.model.RecipeDto
import com.recipe.composerecipe.network.model.RecipeDtoMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapper = RecipeDtoMapper()
        val recipe = Recipe()
        val dto: RecipeDto = mapper.mapFromDomainModel(recipe)
        Log.d("NETWORK", "Mapper = $dto")

        val r: Recipe = mapper.mapToDomainModel(dto)
    }
}

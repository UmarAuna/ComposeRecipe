package com.recipe.composerecipe.di

import com.recipe.composerecipe.network.model.RecipeDtoMapper
import com.recipe.composerecipe.network.responses.RetrofitService
import com.recipe.composerecipe.repository.RecipeRepository
import com.recipe.composerecipe.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        retrofitService: RetrofitService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(retrofitService, recipeDtoMapper)
    }
}

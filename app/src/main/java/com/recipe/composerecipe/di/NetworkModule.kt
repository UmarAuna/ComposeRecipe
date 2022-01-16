package com.recipe.composerecipe.di

import com.google.gson.GsonBuilder
import com.recipe.composerecipe.network.model.RecipeDtoMapper
import com.recipe.composerecipe.network.responses.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun providesAuthToken(): String {
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}

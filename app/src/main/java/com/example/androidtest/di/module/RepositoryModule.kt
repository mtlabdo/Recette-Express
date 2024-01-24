package com.example.androidtest.di.module

import com.example.androidTest.repository.RecipesRemoteRepository
import com.exemple.androidTest.core.repository.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * @Binds indique à Hilt l'implémentation à utiliser lorsqu'il doit fournir une instance d'une interface.
     */
    @Binds
    abstract fun recipesRemoteRecipeRepository(recipesRepository: RecipesRemoteRepository): RecipesRepository

}
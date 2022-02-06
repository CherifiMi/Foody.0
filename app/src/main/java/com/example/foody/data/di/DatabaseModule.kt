package com.example.foody.data.di

import android.content.Context
import androidx.room.Room
import com.example.foody.data.util.Constants.Companion.DATABASE_NAME
import com.example.foody.data.database.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // ? making the database that the room db uses
    // ? the DATABASE_NAME is set on the Constants class

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}
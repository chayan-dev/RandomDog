package com.example.randomdog.di

import android.content.Context
import androidx.room.Room
import com.example.randomdog.db.DogDao
import com.example.randomdog.db.DogsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DogsDatabase {
        return Room.databaseBuilder(
            context,
            DogsDatabase::class.java,
            "DogDB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDogDao(database: DogsDatabase): DogDao {
        return database.getDogsDao()
    }
}
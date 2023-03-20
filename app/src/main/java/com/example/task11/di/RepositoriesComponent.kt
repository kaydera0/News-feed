package com.example.task11.di

import android.content.Context
import com.example.task11.network.Network
import com.example.task11.repositories.RssRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesComponent {

    @Singleton
    @Provides
    fun provideRssRepo(@ApplicationContext context:Context): RssRepository {
        return RssRepository(context)
    }
}
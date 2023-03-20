package com.example.task11.di

import android.content.Context
import androidx.room.Room
import com.example.task11.network.Network
import com.example.task11.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkComponent {

    @Singleton
    @Provides
    fun provideNetwork(): Network {
        return Network()
    }
}
package com.midhun.technical.di

import android.content.Context
import com.midhun.technical.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideNowApiService(@ApplicationContext appContext: Context): ApiService {
        return ApiService(appContext)
    }
}
package com.midhun.technical.di

import com.midhun.technical.network.source.UserDataSource
import com.midhun.technical.network.source.UserDataSourceImpl
import com.midhun.technical.repository.UserRepository
import com.midhun.technical.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UsersRepository


@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindUserDataSource(impl: UserDataSourceImpl): UserDataSource
}

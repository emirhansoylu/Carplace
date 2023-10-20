package dev.duckbuddyy.carplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.duckbuddyy.carplace.network.NetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideNetworkRepository(): NetworkRepository {
        return NetworkRepository()
    }
}
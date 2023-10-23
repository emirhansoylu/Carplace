package dev.duckbuddyy.carplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.duckbuddyy.carplace.BuildConfig
import dev.duckbuddyy.carplace.model.INetworkRepository
import dev.duckbuddyy.carplace.network_ktor.KtorRepository
import dev.duckbuddyy.carplace.network_retrofit.RetrofitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideNetworkRepository(): INetworkRepository {
        return if (BuildConfig.HTTP_CLIENT == "RETROFIT") {
            RetrofitRepository()
        } else {
            KtorRepository()
        }
    }
}
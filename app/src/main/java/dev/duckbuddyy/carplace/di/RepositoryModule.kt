package dev.duckbuddyy.carplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.duckbuddyy.carplace.BuildConfig
import dev.duckbuddyy.carplace.model.IRemoteDataSource
import dev.duckbuddyy.carplace.network_ktor.KtorDataSource
import dev.duckbuddyy.carplace.network_retrofit.RetrofitDataSource
import dev.duckbuddyy.carplace.util.CarplaceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNetworkDataSource(): IRemoteDataSource {
        return if (BuildConfig.HTTP_CLIENT == "RETROFIT") {
            RetrofitDataSource()
        } else {
            KtorDataSource()
        }
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(dataSource: IRemoteDataSource): CarplaceRepository {
        return CarplaceRepository(dataSource)
    }
}
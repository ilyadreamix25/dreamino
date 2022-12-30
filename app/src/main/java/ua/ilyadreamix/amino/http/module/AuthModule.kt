package ua.ilyadreamix.amino.http.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.ilyadreamix.amino.http.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthService() = ApiModule.provideService(AuthRepository::class.java)
}
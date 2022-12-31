package ua.ilyadreamix.amino.http.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.ilyadreamix.amino.http.repository.SecurityRepository

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {
    @Provides
    fun provideService() = ApiModule.provideService(SecurityRepository::class.java)
}
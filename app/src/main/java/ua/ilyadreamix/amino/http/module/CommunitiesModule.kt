package ua.ilyadreamix.amino.http.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.ilyadreamix.amino.http.repository.CommunityRepository

@Module
@InstallIn(SingletonComponent::class)
object CommunitiesModule {
    @Provides
    fun provideService() = ApiModule.provideService(CommunityRepository::class.java)
}
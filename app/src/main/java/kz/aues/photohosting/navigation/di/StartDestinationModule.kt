package kz.aues.photohosting.navigation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.navigation.DefaultDestinationsProvider
import kz.aues.photohosting.navigation.DestinationsProvider

@Module
@InstallIn(SingletonComponent::class)
interface StartDestinationModule {

    @Binds
    fun bindDestinationsProvider(
        provider: DefaultDestinationsProvider
    ): DestinationsProvider

}
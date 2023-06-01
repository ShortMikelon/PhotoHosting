package kz.aues.photohosting.data.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.settings.PreferencesDataStoreSettingsDataSource
import kz.aues.photohosting.data.settings.SettingsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingsSourcesModule {

    @Binds
    @Singleton
    fun bindSettingsSource(
        dataStoreSettingsSource: PreferencesDataStoreSettingsDataSource
    ): SettingsDataSource

}
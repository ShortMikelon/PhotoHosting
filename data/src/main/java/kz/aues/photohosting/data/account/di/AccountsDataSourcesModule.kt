package kz.aues.photohosting.data.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.account.sources.AccountAvatarDataSource
import kz.aues.photohosting.data.account.sources.AccountDataSource
import kz.aues.photohosting.data.account.sources.FirebaseAccountAvatarDataSource
import kz.aues.photohosting.data.account.sources.FirebaseAccountDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountsDataSourcesModule {

    @Binds
    @Singleton
    fun bindAccountDataSource(
        firebaseAccountDataSource: FirebaseAccountDataSource
    ) : AccountDataSource

    @Binds
    @Singleton
    fun bindAccountAvatarDataSource(
        firebaseAccountAvatarDataSource: FirebaseAccountAvatarDataSource
    ) : AccountAvatarDataSource

}
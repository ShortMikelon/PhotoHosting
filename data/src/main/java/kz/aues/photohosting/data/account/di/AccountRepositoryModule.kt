package kz.aues.photohosting.data.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.AccountDataRepository
import kz.aues.photohosting.data.account.DefaultAccountDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountRepositoryModule {

    @Binds
    @Singleton
    fun bindAccountRepository(
        defaultAccountRepository: DefaultAccountDataRepository
    ): AccountDataRepository

}
package kz.aues.photohosting.connector.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.connector.AdapterAccountRepository
import kz.aues.photohosting.connector.AdapterCommentsRepository
import kz.aues.photohosting.connector.AdapterImageRemoteRepository
import kz.aues.photohosting.connector.AdapterPreviewsRepository
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.CommentsRepository
import kz.aues.photohosting.domain.ImageRemoteRepository
import kz.aues.photohosting.domain.PreviewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    @Singleton
    fun bindImageRepository(
        adapterRemoteRepository: AdapterImageRemoteRepository
    ): ImageRemoteRepository


    @Binds
    @Singleton
    fun bindPreviewsRepository(
        adapterPreviewsRepository: AdapterPreviewsRepository
    ): PreviewsRepository

    @Binds
    @Singleton
    fun bindCommentRepository(
        adapterCommentsRepository: AdapterCommentsRepository
    ): CommentsRepository

    @Binds
    @Singleton
    fun bindAccountRepository(
        adapterAccountRepository: AdapterAccountRepository
    ): AccountRepository
}
package kz.aues.photohosting.data.comments.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.CommentsDataRepository
import kz.aues.photohosting.data.comments.DefaultCommentsDataRepository

@Module
@InstallIn(SingletonComponent::class)
interface CommentsDataRepositoriesModule {

    @Binds
    fun bindCommentsDataRepository(
        defaultCommentsDataRepository: DefaultCommentsDataRepository
    ): CommentsDataRepository

}
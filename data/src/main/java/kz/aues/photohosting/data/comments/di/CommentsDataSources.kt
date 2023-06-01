package kz.aues.photohosting.data.comments.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.comments.sources.CommentsDataSource
import kz.aues.photohosting.data.comments.sources.FirebaseCommentsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommentsDataSources {

    @Binds
    @Singleton
    fun bindCommentsDataSource(
        defaultCommentsDataSource: FirebaseCommentsDataSource
    ) : CommentsDataSource

}


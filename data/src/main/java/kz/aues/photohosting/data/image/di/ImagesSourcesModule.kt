package kz.aues.photohosting.data.image.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.image.sources.ImageDataSource
import kz.aues.photohosting.data.image.sources.ImageDescriptionDataSource
import kz.aues.photohosting.data.image.sources.FirebaseStorageImageDataSource
import kz.aues.photohosting.data.image.sources.FirestoreImageDescriptionDataSource

@Module
@InstallIn(SingletonComponent::class)
interface ImagesSourcesModule {

    @Binds
    fun bindImageDataSource(
        imageSource: FirebaseStorageImageDataSource
    ): ImageDataSource

    @Binds
    fun bindImageDescriptionDataSource(
        imageDescriptionDataSource: FirestoreImageDescriptionDataSource
    ): ImageDescriptionDataSource
}
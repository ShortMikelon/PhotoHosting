package kz.aues.photohosting.data.image.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.ImageRemoteDataRepository
import kz.aues.photohosting.data.image.ImageRemoteDataRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface ImagesRepositoriesModule {

    @Binds
    fun bindImageRemoteRepository(
        remoteRepository: ImageRemoteDataRepositoryImpl
    ): ImageRemoteDataRepository

}
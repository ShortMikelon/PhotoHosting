package kz.aues.photohosting.data.previews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.PreviewsDataRepository
import kz.aues.photohosting.data.previews.DefaultPreviewsDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreviewsDataRepositoriesModule {

    @Binds
    fun bindPreviewsDataRepository(
        defaultPreviewsDataRepository: DefaultPreviewsDataRepository
    ) : PreviewsDataRepository

}
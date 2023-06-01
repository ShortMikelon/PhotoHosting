package kz.aues.photohosting.data.previews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.aues.photohosting.data.previews.sources.FirebasePreviewsSource
import kz.aues.photohosting.data.previews.sources.PreviewsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreviewsDataSourcesModule {

    @Binds
    @Singleton
    fun bindPreviewsDataSource(
        firebasePreviewsSource: FirebasePreviewsSource
    ) : PreviewsDataSource

}
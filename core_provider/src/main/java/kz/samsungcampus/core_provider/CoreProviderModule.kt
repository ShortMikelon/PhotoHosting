package kz.samsungcampus.core_provider

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kz.samsungcampus.android.AndroidCoreProvider
import kz.samsungcampus.common.CoreProvider
import kz.samsungcampus.common.DefaultLazyFlowSubjectFactory
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreProviderModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext context: Context
    ): CoreProvider =
        AndroidCoreProvider(context)

    @Provides
    @Singleton
    fun provideLazyFlowSubjectFactory(): LazyFlowSubjectFactory {
        return DefaultLazyFlowSubjectFactory(dispatcher = Dispatchers.IO)
    }
}
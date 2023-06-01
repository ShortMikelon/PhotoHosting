package kz.aues.photohosting.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kz.aues.photohosting.navigation.GlobalNavigationComponentRouter
import kz.samsungcampus.android.ActivityRequired

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    @IntoSet
    fun provideActivityRequires(
        router: GlobalNavigationComponentRouter
    ): ActivityRequired {
        return router
    }

}


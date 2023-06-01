package kz.aues.photohosting

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import kz.samsungcampus.common.Core
import kz.samsungcampus.common.CoreProvider
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    @Inject
    lateinit var coreProvider: CoreProvider

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
        .crossfade(true)
        .build()

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }
}
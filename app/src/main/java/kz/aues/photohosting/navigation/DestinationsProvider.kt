package kz.aues.photohosting.navigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes

interface DestinationsProvider {

    @IdRes
    fun provideStartDestinationId(): Int

    @NavigationRes
    fun provideNavigationGraphId(): Int

    @IdRes
    fun provideTabsDestinationId(): Int

    fun provideMainTabs(): List<NavTab>

    @IdRes
    fun provideTabsStartDestination(): Int
}


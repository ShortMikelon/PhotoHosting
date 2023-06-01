package kz.aues.photohosting.navigation

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kz.aues.photohosting.R
import javax.inject.Inject

class DefaultDestinationsProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DestinationsProvider {

    override fun provideStartDestinationId(): Int = R.id.signInFragment

    override fun provideNavigationGraphId(): Int = R.navigation.main_graph

    override fun provideMainTabs(): List<NavTab> =
        listOf(
            NavTab(
                R.id.previewsFragment,
                context.getString(R.string.previews),
                R.drawable.ic_home
            ),
            NavTab(
                R.id.createImageFragment,
                context.getString(R.string.create_image),
                R.drawable.ic_create_image
            ),
            NavTab(
                R.id.profileFragment,
                context.getString(R.string.profile),
                R.drawable.ic_profile
            )
        )

    override fun provideTabsDestinationId(): Int = R.id.tabsFragment

    override fun provideTabsStartDestination(): Int = R.id.previewsFragment
}
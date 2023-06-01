package kz.aues.photohosting.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes

class NavTab(
    @IdRes val destinationId: Int,
    val title: String,
    @DrawableRes val icon: Int
) : java.io.Serializable
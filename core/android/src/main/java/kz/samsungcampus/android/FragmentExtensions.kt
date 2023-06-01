@file:Suppress("UNCHECKED_CAST", "DEPRECATION")

package kz.samsungcampus.android

import androidx.fragment.app.Fragment

const val ARG_SCREEN = "ARG_SCREEN"

fun <T : BaseScreen> Fragment.args(): T {
    return requireArguments().getSerializable(ARG_SCREEN) as? T
        ?: throw IllegalStateException("Screen args don't exist")
}
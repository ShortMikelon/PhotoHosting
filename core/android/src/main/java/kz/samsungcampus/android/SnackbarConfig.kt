package kz.samsungcampus.android

import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar

data class SnackbarConfig(
    val text: String,
    val duration: Int,
    @ColorRes val backgroundColorId: Int,
    @ColorRes val textColorId: Int
) {
    companion object {
        const val LENGTH_SHORT = Snackbar.LENGTH_SHORT
        const val LENGTH_LONG = Snackbar.LENGTH_LONG
    }
}
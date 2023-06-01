package kz.aues.photohosting.utlis

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kz.samsungcampus.android.SnackbarConfig

fun SnackbarConfig.show(context: Context, view: View) {
    Snackbar.make(view, text, duration)
        .setTextColor(ContextCompat.getColor(context, textColorId))
        .setBackgroundTint(ContextCompat.getColor(context, backgroundColorId))
        .show()
}



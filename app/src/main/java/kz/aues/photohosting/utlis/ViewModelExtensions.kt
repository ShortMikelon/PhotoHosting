package kz.aues.photohosting.utlis

import androidx.lifecycle.ViewModel
import kz.aues.photohosting.R
import kz.samsungcampus.android.SnackbarConfig

fun ViewModel.createWarningSnackbarConfig(
    text: String, duration: Int = SnackbarConfig.LENGTH_SHORT
): SnackbarConfig = SnackbarConfig(
    text = text,
    duration = duration,
    backgroundColorId = R.color.warning_background,
    textColorId = R.color.white
)

fun ViewModel.createSnackbarConfig(
    text: String, duration: Int = SnackbarConfig.LENGTH_SHORT
): SnackbarConfig = SnackbarConfig(
    text = text,
    duration = duration,
    backgroundColorId = R.color.snackbar_background,
    textColorId = R.color.black
)
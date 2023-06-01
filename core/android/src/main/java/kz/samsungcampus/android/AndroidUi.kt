package kz.samsungcampus.android

import android.content.Context
import android.widget.Toast
import kz.samsungcampus.common.AlertDialogConfig
import kz.samsungcampus.common.CommonUi
import kz.samsungcampus.common.CommonUiDuration

class AndroidUi(
    private val context: Context
) : CommonUi {

    override fun toast(text: String, duration: CommonUiDuration) {
        val toastDuration = when (duration) {
            CommonUiDuration.LENGTH_SHORT -> Toast.LENGTH_SHORT
            CommonUiDuration.LENGTH_LONG -> Toast.LENGTH_LONG
        }
        Toast.makeText(context, text, toastDuration).show()
    }


    override suspend fun alertDialog(config: AlertDialogConfig): Boolean {
        TODO("Not yet implemented")
    }
}
package kz.samsungcampus.common

interface CommonUi {

    fun toast(text: String, duration: CommonUiDuration)

    suspend fun alertDialog(config: AlertDialogConfig): Boolean

}


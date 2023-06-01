package kz.samsungcampus.android

import android.content.Context
import kz.samsungcampus.common.SystemResources

class AndroidResources(
    private val applicationContext: Context
) : SystemResources {

    override fun getString(resId: Int): String =
        applicationContext.getString(resId)


    override fun getString(resId: Int, vararg placeholder: Any): String =
        applicationContext.getString(resId, placeholder)


}
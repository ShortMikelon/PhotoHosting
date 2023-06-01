package kz.samsungcampus.common

interface SystemResources {

    fun getString(resId: Int): String

    fun getString(resId: Int, vararg placeholder: Any): String

}
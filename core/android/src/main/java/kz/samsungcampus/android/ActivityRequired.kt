package kz.samsungcampus.android

import androidx.fragment.app.FragmentActivity

interface ActivityRequired {

    fun onCreate(activity: FragmentActivity)

    fun onStart()

    fun onStopped()

    fun onDestroy()

}


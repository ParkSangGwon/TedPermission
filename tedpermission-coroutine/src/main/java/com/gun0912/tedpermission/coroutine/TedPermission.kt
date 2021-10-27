package com.gun0912.tedpermission.coroutine

import android.annotation.SuppressLint
import com.gun0912.tedpermission.PermissionBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermissionResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("StaticFieldLeak")
object TedPermission {

    fun create(): Builder = Builder()


    class Builder : PermissionBuilder<Builder>() {

        override fun setPermissionListener(listener: PermissionListener): Builder {
            throw UnsupportedOperationException("Use check() function")
        }

        suspend fun check(): TedPermissionResult = suspendCoroutine {
            super.setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    it.resume(TedPermissionResult(null))
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    it.resume(TedPermissionResult(deniedPermissions))
                }
            })
            checkPermissions()
        }

        suspend fun checkGranted(): Boolean = check().isGranted
    }

}
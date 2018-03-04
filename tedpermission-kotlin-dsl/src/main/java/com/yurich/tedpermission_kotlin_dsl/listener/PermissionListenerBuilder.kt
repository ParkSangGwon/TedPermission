@file:Suppress("PackageName")

package com.yurich.tedpermission_kotlin_dsl.listener

import com.gun0912.tedpermission.PermissionListener

class PermissionListenerBuilder {

    private var onPermissionGrantedListener: () -> Unit = {}

    private var onPermissionDeniedListener: (ArrayList<String>?) -> Unit = {}

    fun onPermissionGranted(body: () -> Unit): PermissionListenerBuilder {
        this.onPermissionGrantedListener = body
        return this
    }

    fun onPermissionDenied(body: (ArrayList<String>?) -> Unit): PermissionListenerBuilder {
        this.onPermissionDeniedListener = body
        return this
    }

    fun build() = object : PermissionListener {

        override fun onPermissionGranted() {
            onPermissionGrantedListener()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            onPermissionDeniedListener(deniedPermissions)
        }

    }

}
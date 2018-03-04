@file:JvmName("CheckPermissions")

package com.yurich.tedpermission_kotlin_dsl

import android.content.Context

fun Context.checkPermissions(body: Dsl.() -> Unit) {
    val tedPermissionBuilder = Dsl(this).apply(body).check()
}
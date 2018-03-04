@file:JvmName("CheckPermissions")

package com.gun0912.tedpermission_kotlin_dsl.dsl

import android.content.Context

fun Context.checkPermissions(body: Dsl.() -> Unit) =
    Dsl(this).apply(body).check()
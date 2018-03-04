@file:JvmName("CheckPermissions")

package com.yurich.tedpermission_kotlin_dsl.dsl

import android.content.Context

fun Context.checkPermissions(body: Dsl.() -> Unit) =
    Dsl(this).apply(body).check()
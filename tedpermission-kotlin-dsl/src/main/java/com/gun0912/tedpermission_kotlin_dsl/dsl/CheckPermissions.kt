@file:JvmName("CheckPermissions")

package com.gun0912.tedpermission_kotlin_dsl.dsl

import android.content.Context

inline fun Context.checkPermissions(body: Dsl.() -> Unit) =
    Dsl(this).apply(body).check()

// There are three variants of permission calling
// because using varargs can cause performance issues
// and users rarely ask for more than 2 permissions at once
inline fun Context.checkPermissions(permission: String, body: Dsl.() -> Unit) =
    Dsl(this)
            .apply {
                +permission
                body()
            }
            .check()

inline fun Context.checkPermissions(firstPermission: String, secondPermission: String, body: Dsl.() -> Unit) =
        Dsl(this)
                .apply {
                    +firstPermission
                    +secondPermission
                    body()
                }
                .check()

inline fun Context.checkPermissions(vararg permissions: String, body: Dsl.() -> Unit) =
        Dsl(this)
                .apply {
                    +permissions.toList()
                    body()
                }
                .check()
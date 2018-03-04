package com.yurich.tedpermission_kotlin_dsl.dsl

import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
import android.support.annotation.StringRes
import com.gun0912.tedpermission.PermissionBuilder
import com.gun0912.tedpermission.TedPermission
import com.yurich.tedpermission_kotlin_dsl.listener.PermissionListenerBuilder

class Dsl(context: Context) {

    val tedPermission = TedPermission.with(context)

    fun withPermissionListener(body: PermissionListenerBuilder.() -> Unit) {
        tedPermission.setPermissionListener(
                PermissionListenerBuilder().apply(body).build()
        )
    }

    operator fun String.unaryPlus() {
        tedPermission.addSinglePermission(this)
    }

    operator fun List<String>.unaryPlus() {
        tedPermission.addPermissions(this)
    }

    operator fun Array<String>.unaryPlus() {
        tedPermission.addPermissions(this.asList())
    }

    @PermissionBuilder.ScreenOrientation
    var screenOrientation: Int = SCREEN_ORIENTATION_UNSPECIFIED
        set(newValue) {
            tedPermission.setScreenOrientation(newValue)
        }

    fun rationale(body: Rationale.() -> Unit) {
        Rationale().apply(body)
    }

    fun inSettings(body: GoToSettings.() -> Unit) {
        tedPermission.setGotoSettingButton(true)
        GoToSettings().apply(body)
    }

    fun onDeny(body: Denial.() -> Unit) {
        Denial().apply(body)
    }

    fun check() {
        tedPermission.check()
    }

    inner class Rationale {

        fun title(title: CharSequence) {
            tedPermission.setRationaleTitle(title)
        }

        fun title(@StringRes title: Int) {
            tedPermission.setRationaleTitle(title)
        }

        fun message(message: CharSequence) {
            tedPermission.setRationaleMessage(message)
        }

        fun message(@StringRes message: Int) {
            tedPermission.setRationaleMessage(message)
        }

        fun confirm(confirm: CharSequence) {
            tedPermission.setRationaleConfirmText(confirm)
        }

        fun confirm(@StringRes confirm: Int) {
            tedPermission.setRationaleConfirmText(confirm)
        }

    }

    inner class GoToSettings {

        fun closeButtonText(@StringRes buttonText: Int) {
            tedPermission.setGotoSettingButtonText(buttonText)
        }

        fun closeButtonText(buttonText: CharSequence) {
            tedPermission.setGotoSettingButtonText(buttonText)
        }

    }

    inner class Denial {

        fun message(@StringRes message: Int) {
            tedPermission.setDeniedMessage(message)
        }

        fun message(message: CharSequence) {
            tedPermission.setDeniedMessage(message)
        }

        fun title(@StringRes title: Int) {
            tedPermission.setDeniedTitle(title)
        }

        fun title(title: CharSequence) {
            tedPermission.setDeniedTitle(title)
        }

        fun closeButtonText(@StringRes buttonText: Int) {
            tedPermission.setDeniedCloseButtonText(buttonText)
        }

        fun closeButtonText(buttonText: CharSequence) {
            tedPermission.setDeniedCloseButtonText(buttonText)
        }
    }
}
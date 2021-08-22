package com.gun0912.tedpermissiondemo

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch


class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            val permissionResult = TedPermission.create()
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                    "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
                )
                .setGotoSettingButtonText("bla bla")
                .setPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .check()

            Log.d("ted", "permissionResult: $permissionResult")
        }
    }
}
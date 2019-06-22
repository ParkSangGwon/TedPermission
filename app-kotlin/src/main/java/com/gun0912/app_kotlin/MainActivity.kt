package com.gun0912.app_kotlin

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gun0912.tedpermission_kotlin_dsl.dsl.checkPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionsForCamera()

        if (Build.VERSION.SDK_INT >= 16) {
            requestPermissionsForStorage()
        } else {
            requestPermissionsForStorageOnPreJellyBean()
        }

        requestLocationPermission()

        checkPermissions {

            +Manifest.permission.READ_CONTACTS

            withPermissionListener {

                onPermissionGranted {
                    Toast.makeText(this@MainActivity, "Permissions are granted", Toast.LENGTH_SHORT).show()
                }

                onPermissionDenied {
                    denied_permissions.text = it?.joinToString(", ")
                }

            }

            rationale {
                title(R.string.rationale_title)
                message(R.string.rationale_message)
            }

            onDeny {
                title { "Permission denied" }
                message {
                    "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
                }
            }

            onProceedingToSettings {
                closeButtonText(R.string.custom_close_text)
            }

        }
    }

    private fun requestLocationPermission() {
        checkPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION) {
            withPermissionListener {

                onPermissionGranted {
                    Toast.makeText(this@MainActivity, "Permission for locations is granted", Toast.LENGTH_SHORT).show()
                }

                onPermissionDenied {
                    denied_permissions.text = it?.joinToString(", ")
                }

            }
        }
    }

    @TargetApi(16)
    private fun requestPermissionsForStorage() {
        checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE) {
            withPermissionListener {

                onPermissionGranted {
                    Toast.makeText(this@MainActivity, "Permission for storage is granted", Toast.LENGTH_SHORT).show()
                }

                onPermissionDenied {
                    denied_permissions.text = it?.joinToString(", ")
                }

            }
        }
    }

    private fun requestPermissionsForStorageOnPreJellyBean() {
        checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            withPermissionListener {

                onPermissionGranted {
                    Toast.makeText(this@MainActivity, "Permission for storage on Pre-JellyBean is granted", Toast.LENGTH_SHORT).show()
                }

                onPermissionDenied {
                    denied_permissions.text = it?.joinToString(", ")
                }

            }
        }
    }

    private fun requestPermissionsForCamera() {
        checkPermissions(Manifest.permission.CAMERA) {
            withPermissionListener {

                onPermissionGranted {
                    Toast.makeText(this@MainActivity, "Permission for camera is granted", Toast.LENGTH_SHORT).show()
                }

                onPermissionDenied {
                    denied_permissions.text = it?.joinToString(", ")
                }

            }
        }
    }
}

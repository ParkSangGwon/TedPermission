package com.yurich.app_kotlin

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.yurich.tedpermission_kotlin_dsl.dsl.checkPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions {
            +Manifest.permission.CAMERA
            +Manifest.permission.READ_CONTACTS
            +listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

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
                title("Permission denied")
                message("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            }

            inSettings {
                closeButtonText("Go to settings")
            }

        }
    }
}

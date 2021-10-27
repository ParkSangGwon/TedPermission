package com.gun0912.tedpermissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionUtil;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

/**
 * Created by babosamo on 16. 10. 4..
 */
public class WindowPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isAlertWindowPermissionGranted = TedPermissionUtil.isGranted(Manifest.permission.SYSTEM_ALERT_WINDOW);
        Log.d("ted", "isAlertWindowPermissionGranted: " + isAlertWindowPermissionGranted);


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(WindowPermissionActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(WindowPermissionActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };


        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("we need permission for read contact, find your location and system alert window")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("setting")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SYSTEM_ALERT_WINDOW)
                .check();
    }
}

package com.gun0912.tedpermissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.tedpark.tedpermission.rx1.TedRxPermission;
import java.util.List;

/**
 * Created by TedPark on 16. 2. 21..
 */
public class RxJava1Activity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    PermissionListener permissionlistener = new PermissionListener() {
      @Override
      public void onPermissionGranted() {
        Toast.makeText(RxJava1Activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onPermissionDenied(List<String> deniedPermissions) {
        Toast.makeText(RxJava1Activity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
            .show();
      }


    };

    TedRxPermission.with(this)
        .setDeniedMessage(
            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
        .request()
        .subscribe(tedPermissionResult -> {
          if (tedPermissionResult.isGranted()) {
            Toast.makeText(RxJava1Activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(RxJava1Activity.this,
                "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
                .show();
          }
        }, throwable -> {
        });


  }
}

package com.gun0912.tedpermissiondemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.TedPermissionUtil;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_normal).setOnClickListener(this);
        findViewById(R.id.btn_rxjava2).setOnClickListener(this);
        findViewById(R.id.btn_rxjava3).setOnClickListener(this);
        findViewById(R.id.btn_coroutine).setOnClickListener(this);
        findViewById(R.id.btn_windowPermission).setOnClickListener(this);

        boolean isGranted = TedPermissionUtil.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("ted", "isGranted: " + isGranted);
        List<String> deniedPermissions = TedPermissionUtil.getDeniedPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("ted", "deniedPermissions: " + deniedPermissions);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent = null;

        switch (id) {
            case R.id.btn_normal:
                intent = new Intent(this, NormalActivity.class);
                break;

            case R.id.btn_rxjava2:
                intent = new Intent(this, RxJava2Activity.class);
                break;

            case R.id.btn_rxjava3:
                intent = new Intent(this, RxJava3Activity.class);
                break;

            case R.id.btn_coroutine:
                intent = new Intent(this, CoroutineActivity.class);
                break;

            case R.id.btn_windowPermission:
                intent = new Intent(this, WindowPermissionActivity.class);
                break;

        }

        if (intent != null) {
            startActivity(intent);
        }

    }
}

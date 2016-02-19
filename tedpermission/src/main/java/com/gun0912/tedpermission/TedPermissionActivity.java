package com.gun0912.tedpermission;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.gun0912.tedpermission.busevent.TedBusProvider;
import com.gun0912.tedpermission.busevent.TedPermissionEvent;
import com.gun0912.tedpermission.util.Dlog;

import java.util.ArrayList;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedPermissionActivity extends Activity {

    public static final int REQ_CODE_PERMISSION_REQUEST = 7777;
    public static final int REQ_CODE_REQUEST_SETTING = 8888;

    public static final String EXTRA_PERMISSIONS = "permissions";
    public static final String EXTRA_DENY_MESSAGE = "deny_message";
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static final String EXTRA_SETTING_BUTTON = "setting_button";
    public static final String EXTRA_DENIED_DIALOG_CLOSE_TEXT = "denied_dialog_close_text";

    String denyMessage;
    String[] permissions;
    String packageName;
    boolean hasSettingButton;

    int deniedCloseButtonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        setupFromSavedInstanceState(savedInstanceState);
        checkPermissions(false);
    }


    private void setupFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            permissions = savedInstanceState.getStringArray(EXTRA_PERMISSIONS);
            denyMessage = savedInstanceState.getString(EXTRA_DENY_MESSAGE);
            packageName = savedInstanceState.getString(EXTRA_PACKAGE_NAME);
            hasSettingButton = savedInstanceState.getBoolean(EXTRA_SETTING_BUTTON, true);
            deniedCloseButtonText = savedInstanceState.getInt(EXTRA_DENIED_DIALOG_CLOSE_TEXT,R.string.close);
        } else {

            Intent intent = getIntent();
            permissions = intent.getStringArrayExtra(EXTRA_PERMISSIONS);
            denyMessage = intent.getStringExtra(EXTRA_DENY_MESSAGE);
            packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME);
            hasSettingButton = intent.getBooleanExtra(EXTRA_SETTING_BUTTON, true);
            deniedCloseButtonText =intent.getIntExtra(EXTRA_DENIED_DIALOG_CLOSE_TEXT,R.string.close);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArray(EXTRA_PERMISSIONS, permissions);
        outState.putString(EXTRA_DENY_MESSAGE, denyMessage);
        outState.putString(EXTRA_PACKAGE_NAME, packageName);
        outState.putBoolean(EXTRA_SETTING_BUTTON,hasSettingButton);
        outState.putInt(EXTRA_SETTING_BUTTON,deniedCloseButtonText);

        super.onSaveInstanceState(outState);
    }


    private void permissionGranted() {
        TedBusProvider.getInstance().post(new TedPermissionEvent(true, null));
        finish();
    }

    private void permissionDenied(ArrayList<String> deniedpermissions) {
        TedBusProvider.getInstance().post(new TedPermissionEvent(false, deniedpermissions));
        finish();
    }


    private void checkPermissions(boolean fromOnActivityResult) {
        Dlog.d("");

        ArrayList<String> needPermissions = new ArrayList<>();


        for (String permission : permissions) {
            Dlog.d("permission: " + permission);


            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermissions.add(permission);
            }

        }


        if (needPermissions.isEmpty()) {
            permissionGranted();
        } else {


            if (fromOnActivityResult) {
                permissionDenied(needPermissions);
            } else {
                Dlog.d("");
                ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQ_CODE_PERMISSION_REQUEST);
            }


        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        Dlog.d("");
        ArrayList<String> deniedPermissions = new ArrayList<>();


        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }

        if (deniedPermissions.isEmpty()) {
            permissionGranted();
        } else {


            showPermissionDenyDialog(deniedPermissions);


        }


    }


    public void showPermissionDenyDialog(final ArrayList<String> deniedPermissions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage(denyMessage)
                .setCancelable(false)

                .setNegativeButton(getString(deniedCloseButtonText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        permissionDenied(deniedPermissions);
                    }
                });

        if (hasSettingButton) {

            builder.setPositiveButton(getString(R.string.setting), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.parse("package:" + packageName));
                        startActivityForResult(intent, REQ_CODE_REQUEST_SETTING);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Dlog.e(e.toString());
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivityForResult(intent, REQ_CODE_REQUEST_SETTING);
                    }

                }
            });

        }


        builder.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_REQUEST_SETTING:
                checkPermissions(true);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

}

package com.gun0912.tedpermission;

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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;

import com.gun0912.tedpermission.busevent.TedBusProvider;
import com.gun0912.tedpermission.busevent.TedPermissionEvent;
import com.gun0912.tedpermission.util.Dlog;

import java.util.ArrayList;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedPermissionActivity extends AppCompatActivity {

    public static final int REQ_CODE_PERMISSION_REQUEST = 10;
    public static final int REQ_CODE_REQUEST_SETTING = 20;


    public static final String EXTRA_PERMISSIONS = "permissions";
    public static final String EXTRA_RATIONALE_MESSAGE = "rationale_message";
    public static final String EXTRA_DENY_MESSAGE = "deny_message";
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static final String EXTRA_SETTING_BUTTON = "setting_button";
    public static final String EXTRA_SETTING_BUTTON_TEXT = "setting_button_text";
    public static final String EXTRA_RATIONALE_CONFIRM_TEXT = "rationale_confirm_text";
    public static final String EXTRA_DENIED_DIALOG_CLOSE_TEXT = "denied_dialog_close_text";

    String rationale_message;
    String denyMessage;
    String[] permissions;
    String packageName;
    boolean hasSettingButton;
    String settingButtonText;

    String deniedCloseButtonText;
    String rationaleConfirmText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dlog.d("");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        setupFromSavedInstanceState(savedInstanceState);
        checkPermissions(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dlog.d("");
    }

    private void setupFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            permissions = savedInstanceState.getStringArray(EXTRA_PERMISSIONS);
            rationale_message = savedInstanceState.getString(EXTRA_RATIONALE_MESSAGE);
            denyMessage = savedInstanceState.getString(EXTRA_DENY_MESSAGE);
            packageName = savedInstanceState.getString(EXTRA_PACKAGE_NAME);


            hasSettingButton = savedInstanceState.getBoolean(EXTRA_SETTING_BUTTON, true);

            rationaleConfirmText = savedInstanceState.getString(EXTRA_RATIONALE_CONFIRM_TEXT);
            deniedCloseButtonText = savedInstanceState.getString(EXTRA_DENIED_DIALOG_CLOSE_TEXT);


            settingButtonText =savedInstanceState.getString(EXTRA_SETTING_BUTTON_TEXT);
        } else {

            Intent intent = getIntent();
            permissions = intent.getStringArrayExtra(EXTRA_PERMISSIONS);
            rationale_message = intent.getStringExtra(EXTRA_RATIONALE_MESSAGE);
            denyMessage = intent.getStringExtra(EXTRA_DENY_MESSAGE);
            packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME);
            hasSettingButton = intent.getBooleanExtra(EXTRA_SETTING_BUTTON, true);
            rationaleConfirmText = intent.getStringExtra(EXTRA_RATIONALE_CONFIRM_TEXT);
            deniedCloseButtonText = intent.getStringExtra(EXTRA_DENIED_DIALOG_CLOSE_TEXT);
            settingButtonText = intent.getStringExtra(EXTRA_SETTING_BUTTON_TEXT);

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArray(EXTRA_PERMISSIONS, permissions);
        outState.putString(EXTRA_RATIONALE_MESSAGE, rationale_message);
        outState.putString(EXTRA_DENY_MESSAGE, denyMessage);
        outState.putString(EXTRA_PACKAGE_NAME, packageName);
        outState.putBoolean(EXTRA_SETTING_BUTTON, hasSettingButton);
        outState.putString(EXTRA_SETTING_BUTTON, deniedCloseButtonText);
        outState.putString(EXTRA_RATIONALE_CONFIRM_TEXT, rationaleConfirmText);
        outState.putString(EXTRA_SETTING_BUTTON_TEXT, settingButtonText);

        super.onSaveInstanceState(outState);
    }


    private void permissionGranted() {
        TedBusProvider.getInstance().post(new TedPermissionEvent(true, null));
        finish();
        overridePendingTransition(0, 0);
    }

    private void permissionDenied(ArrayList<String> deniedpermissions) {
        TedBusProvider.getInstance().post(new TedPermissionEvent(false, deniedpermissions));
        finish();
        overridePendingTransition(0, 0);
    }


    private void checkPermissions(boolean fromOnActivityResult) {
        Dlog.d("");

        ArrayList<String> needPermissions = new ArrayList<>();

        boolean showRationale = false;

        for (String permission : permissions) {


            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermissions.add(permission);
                showRationale=true;
            }

        }


/*

        for(String permission:needPermissions){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                showRationale=true;
            }

        }

*/


        if (needPermissions.isEmpty()) {
            permissionGranted();
        }
        //From Setting Activity
        else if (fromOnActivityResult) {
            permissionDenied(needPermissions);
        }
        //Need Show Rationale
        else if (showRationale && !TextUtils.isEmpty(rationale_message)) {

            showRationaleDialog(needPermissions);


        }
        //Need Request Permissions
        else {

            requestPermissions(needPermissions);


        }


    }

    public void requestPermissions(ArrayList<String> needPermissions) {
        ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQ_CODE_PERMISSION_REQUEST);

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



    private void showRationaleDialog(final ArrayList<String> needPermissions) {

        new AlertDialog.Builder(this)
                .setMessage(rationale_message)
                .setCancelable(false)

                .setNegativeButton(rationaleConfirmText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions(needPermissions);

                    }
                })
                .show();


    }

    public void showPermissionDenyDialog(final ArrayList<String> deniedPermissions) {

        if (TextUtils.isEmpty(denyMessage)) {
            // denyMessage 설정 안함
            permissionDenied(deniedPermissions);
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage(denyMessage)
                .setCancelable(false)

                .setNegativeButton(deniedCloseButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        permissionDenied(deniedPermissions);
                    }
                });

        if (hasSettingButton) {


            if(TextUtils.isEmpty(settingButtonText)){
                settingButtonText = getString(R.string.tedpermission_setting);
            }

            builder.setPositiveButton(settingButtonText, new DialogInterface.OnClickListener() {
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

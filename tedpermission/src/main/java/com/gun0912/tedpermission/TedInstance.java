package com.gun0912.tedpermission;

import android.content.Context;
import android.content.Intent;

import com.gun0912.tedpermission.busevent.BusProvider;
import com.gun0912.tedpermission.busevent.PermissionEvent;
import com.squareup.otto.Subscribe;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedInstance {

    public PermissionListener listener;
    public String[] permissions;
    public String denyMessage;
    public boolean hasSettingBtn = true;
    public int deniedCloseButtonText=-1;
    Context context;


    public TedInstance(Context context) {

        this.context = context;

        BusProvider.getInstance().register(this);
    }


    public void checkPermissions() {

        //start transparent activity

        Intent intent = new Intent(context, TedPermissionActivity.class);
        intent.putExtra(TedPermissionActivity.EXTRA_PERMISSIONS, permissions);
        intent.putExtra(TedPermissionActivity.EXTRA_DENY_MESSAGE, denyMessage);
        intent.putExtra(TedPermissionActivity.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(TedPermissionActivity.EXTRA_SETTING_BUTTON, hasSettingBtn);
        intent.putExtra(TedPermissionActivity.EXTRA_DENIED_DIALOG_CLOSE_TEXT, deniedCloseButtonText);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Subscribe
    public void onPermissionResult(PermissionEvent event) {

        if (event.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionDenied(event.getDeniedPermissions());
        }


    }

}

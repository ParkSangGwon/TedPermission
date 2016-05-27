package com.gun0912.tedpermission;

import android.content.Context;
import android.content.Intent;

import com.gun0912.tedpermission.busevent.TedBusProvider;
import com.gun0912.tedpermission.busevent.TedPermissionEvent;
import com.gun0912.tedpermission.util.Dlog;
import com.squareup.otto.Subscribe;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedInstance {

    public PermissionListener listener;
    public String[] permissions;
    public String rationaleMessage;
    public String denyMessage;
    public String settingButtonText;
    public boolean hasSettingBtn = true;

    public String deniedCloseButtonText;
    public String rationaleConfirmText;
    Context context;


    public TedInstance(Context context) {

        this.context = context;

        TedBusProvider.getInstance().register(this);

        deniedCloseButtonText = context.getString(R.string.tedpermission_close);
        rationaleConfirmText = context.getString(R.string.tedpermission_confirm);
    }


    public void checkPermissions() {
        Dlog.d("");

        Intent intent = new Intent(context, TedPermissionActivity.class);
        intent.putExtra(TedPermissionActivity.EXTRA_PERMISSIONS, permissions);

        intent.putExtra(TedPermissionActivity.EXTRA_RATIONALE_MESSAGE, rationaleMessage);
        intent.putExtra(TedPermissionActivity.EXTRA_DENY_MESSAGE, denyMessage);
        intent.putExtra(TedPermissionActivity.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(TedPermissionActivity.EXTRA_SETTING_BUTTON, hasSettingBtn);
        intent.putExtra(TedPermissionActivity.EXTRA_DENIED_DIALOG_CLOSE_TEXT, deniedCloseButtonText);
        intent.putExtra(TedPermissionActivity.EXTRA_RATIONALE_CONFIRM_TEXT, rationaleConfirmText);
        intent.putExtra(TedPermissionActivity.EXTRA_SETTING_BUTTON_TEXT, settingButtonText);


        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }


    @Subscribe
    public void onPermissionResult(TedPermissionEvent event) {
        Dlog.d("");
        if (event.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionDenied(event.getDeniedPermissions());
        }
        TedBusProvider.getInstance().unregister(this);


    }

}

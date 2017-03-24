package com.gun0912.tedpermission;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;

import com.gun0912.tedpermission.util.Dlog;
import com.gun0912.tedpermission.util.ObjectUtils;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedPermission {


    private TedInstance instance;


    public TedPermission(Context context) {
        instance = new TedInstance(context);
    }


    public TedPermission setPermissionListener(PermissionListener listener) {

        instance.listener = listener;

        return this;
    }


    public TedPermission setPermissions(String... permissions) {

        instance.permissions = permissions;
        return this;
    }

    public TedPermission setRationaleMessage(CharSequence rationaleMessage) {

        instance.rationaleMessage = rationaleMessage;
        return this;
    }


    public TedPermission setRationaleMessage(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for RationaleMessage");

        instance.rationaleMessage = instance.context.getText(stringRes);
        return this;
    }

    public TedPermission setRationaleTitle(CharSequence rationaleMessage) {

        instance.rationaleTitle = rationaleMessage;
        return this;
    }


    public TedPermission setRationaleTitle(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for RationaleTitle");

        instance.rationaleTitle = instance.context.getText(stringRes);
        return this;
    }

    public TedPermission setDeniedMessage(String denyMessage) {

        instance.denyMessage = denyMessage;
        return this;
    }


    public TedPermission setDeniedMessage(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for DeniedMessage");

        instance.denyMessage = instance.context.getText(stringRes);
        return this;
    }

    public TedPermission setDeniedTitle(String denyTitle) {

        instance.denyTitle = denyTitle;
        return this;
    }


    public TedPermission setDeniedTitle(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for DeniedTitle");

        instance.denyTitle = instance.context.getText(stringRes);
        return this;
    }

    public TedPermission setGotoSettingButton(boolean hasSettingBtn) {

        instance.hasSettingBtn = hasSettingBtn;
        return this;
    }


    public TedPermission setGotoSettingButtonText(String rationaleConfirmText) {

        instance.settingButtonText = rationaleConfirmText;
        return this;
    }


    public TedPermission setGotoSettingButtonText(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for setGotoSettingButtonText");


        instance.settingButtonText = instance.context.getString(stringRes);

        return this;
    }


    public TedPermission setRationaleConfirmText(String rationaleConfirmText) {

        instance.rationaleConfirmText = rationaleConfirmText;
        return this;
    }


    public TedPermission setRationaleConfirmText(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for RationaleConfirmText");


        instance.rationaleConfirmText = instance.context.getString(stringRes);

        return this;
    }


    public TedPermission setDeniedCloseButtonText(String deniedCloseButtonText) {

        instance.deniedCloseButtonText = deniedCloseButtonText;
        return this;
    }


    public TedPermission setDeniedCloseButtonText(@StringRes int stringRes) {

        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for DeniedCloseButtonText");


        instance.deniedCloseButtonText = instance.context.getString(stringRes);

        return this;
    }


    public void check() {


        if (instance.listener == null) {
            throw new NullPointerException("You must setPermissionListener() on TedPermission");
        } else if (ObjectUtils.isEmpty(instance.permissions)) {
            throw new NullPointerException("You must setPermissions() on TedPermission");
        }

        if (ObjectUtils.isEmpty(instance.permissions)) {
            throw new NullPointerException("You must set permission");
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Dlog.d("preMarshmallow");
            instance.listener.onPermissionGranted();

        } else {
            Dlog.d("Marshmallow");
            instance.checkPermissions();
        }


    }


}

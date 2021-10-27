package com.gun0912.tedpermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.provider.TedPermissionProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TedPark on 2017. 9. 26..
 */

public class TedPermissionUtil {
    public static final int REQ_CODE_REQUEST_SETTING = 2000;
    private static final String PREFS_NAME_PERMISSION = "PREFS_NAME_PERMISSION";
    private static final String PREFS_IS_FIRST_REQUEST = "IS_FIRST_REQUEST";

    @SuppressLint("StaticFieldLeak")
    private static final Context context = TedPermissionProvider.context;

    public static boolean isGranted(@NonNull String... permissions) {
        for (String permission : permissions) {
            if (isDenied(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDenied(@NonNull String permission) {
        return !isGranted(permission);
    }

    private static boolean isGranted(@NonNull String permission) {
        if (permission.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return Settings.canDrawOverlays(context);
            } else {
                return true;
            }
        } else {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static List<String> getDeniedPermissions(@NonNull String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (isDenied(permission)) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    public static boolean canRequestPermission(Activity activity, @NonNull String... permissions) {

        if (isFirstRequest(permissions)) {
            return true;
        }

        for (String permission : permissions) {
            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if (isDenied(permission) && !showRationale) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFirstRequest(@NonNull String[] permissions) {
        for (String permission : permissions) {
            if (!isFirstRequest(permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFirstRequest(String permission) {
        return getSharedPreferences().getBoolean(getPrefsNamePermission(permission), true);
    }

    private static String getPrefsNamePermission(String permission) {
        return PREFS_IS_FIRST_REQUEST + "_" + permission;
    }

    private static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFS_NAME_PERMISSION, Context.MODE_PRIVATE);
    }

    public static void startSettingActivityForResult(Activity activity) {
        startSettingActivityForResult(activity, REQ_CODE_REQUEST_SETTING);
    }

    public static void startSettingActivityForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(getSettingIntent(), requestCode);
    }

    public static Intent getSettingIntent() {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.getPackageName()));
    }

    public static void startSettingActivityForResult(Fragment fragment) {
        startSettingActivityForResult(fragment, REQ_CODE_REQUEST_SETTING);
    }

    public static void startSettingActivityForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getSettingIntent(), requestCode);
    }

    static void setFirstRequest(@NonNull String[] permissions) {
        for (String permission : permissions) {
            setFirstRequest(permission);
        }
    }

    private static void setFirstRequest(String permission) {
        getSharedPreferences().edit().putBoolean(getPrefsNamePermission(permission), false).apply();
    }


}

package com.gun0912.tedpermission.normal;

import com.gun0912.tedpermission.PermissionBuilder;

public class TedPermission  {
    public static final String TAG = TedPermission.class.getSimpleName();

    public static Builder create() {
        return new Builder();
    }

    public static class Builder extends PermissionBuilder<Builder> {

        public void check() {
            checkPermissions();
        }

    }
}

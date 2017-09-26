package com.gun0912.tedpermission;

import android.content.Context;

public class TedPermission extends TedPermissionBase{
  public static final String TAG=TedPermission.class.getSimpleName();

  public static Builder with(Context context) {
    return new Builder(context);
  }

    public static class Builder extends PermissionBuilder<Builder> {

    private Builder(Context context) {
      super(context);
    }

    public void check() {
      checkPermissions();
    }

  }
}

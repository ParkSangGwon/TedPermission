package com.gun0912.tedpermission;

import java.util.List;

public interface PermissionListener {

  void onPermissionGranted();

  void onPermissionDenied(List<String> deniedPermissions);

}

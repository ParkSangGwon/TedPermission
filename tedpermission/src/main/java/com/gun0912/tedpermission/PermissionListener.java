package com.gun0912.tedpermission;

import java.util.ArrayList;

public interface PermissionListener {

  void onPermissionGranted();

  void onPermissionDenied(ArrayList<String> deniedPermissions);

}

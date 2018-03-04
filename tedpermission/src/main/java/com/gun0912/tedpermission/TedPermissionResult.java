package com.gun0912.tedpermission;

import com.gun0912.tedpermission.util.ObjectUtils;
import java.util.ArrayList;

public class TedPermissionResult {

  private boolean granted;
  private ArrayList<String> deniedPermissions;

  public TedPermissionResult(ArrayList<String> deniedPermissions) {
    this.granted = ObjectUtils.isEmpty(deniedPermissions);
    this.deniedPermissions = deniedPermissions;
  }

  public boolean isGranted() {
    return granted;
  }

  public ArrayList<String> getDeniedPermissions() {
    return deniedPermissions;
  }
}

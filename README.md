[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TedPermission-green.svg?style=true)](https://android-arsenal.com/details/1/3238)

# What is TedPermission?

After the update to Android 6.0 Marshmallow, we have to not only declare permissions in `AndroidManifest.xml`, but also request them at runtime. Furthermore, the user can turn permissions on/off anytime in application settings. 
<br/>When you use **dangerous permissons**(ex. `CAMERA`, `READ_CONTACTS`, `READ_PHONE_STATE`, ...), you must check and request them at runtime.<br/>
(https://developer.android.com/guide/topics/permissions/overview?hl=en#normal-dangerous)

You can make your own permission check logic [like this](http://developer.android.com/intl/ko/training/permissions/requesting.html), but  it's very complex, mainly because functions Google offer are very hard to use: `checkSelfPermission()`, `requestPermissions()`, `onRequestPermissionsResult()`, `onActivityResult()`.

TedPermission makes it easy to check and request android permissions.


(For Korean)
아래 블로그를 통해 마시멜로우 권한 관련된 사항을 알아보세요
<br/>http://gun0912.tistory.com/55
<br/><br/>



## Demo


<br/><br/>

![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/Screenshot.png?raw=true)    
           
           
1. Request permissions.
2. If user denied permissions, a message dialog with a button to go to Settings will appear. 


## Setup


### Gradle

Edit `root/app/build.gradle` like below.

#### Normal
[ ![Download](https://api.bintray.com/packages/tkdrnjs0912/maven/tedpermission/images/download.svg) ](https://bintray.com/tkdrnjs0912/maven/tedpermission/_latestVersion)
```gradle
dependencies {
    implementation 'gun0912.ted:tedpermission:x.y.z'
}
```

#### RxJava1
[ ![Download](https://api.bintray.com/packages/tkdrnjs0912/maven/tedpermission-rx1/images/download.svg) ](https://bintray.com/tkdrnjs0912/maven/tedpermission-rx1/_latestVersion)
```gradle
dependencies {
    implementation 'gun0912.ted:tedpermission-rx1:x.y.z'
}
```

#### RxJava2
[ ![Download](https://api.bintray.com/packages/tkdrnjs0912/maven/tedpermission-rx2/images/download.svg) ](https://bintray.com/tkdrnjs0912/maven/tedpermission-rx2/_latestVersion)
```gradle
dependencies {
    implementation 'gun0912.ted:tedpermission-rx2:x.y.z'
}
```

If you think this library is useful, please press the star button at the top.
<br/>
<img src="https://phaser.io/content/news/2015/09/10000-stars.png" width="200">

<br/><br/>

## How to use

### Normal
#### -Make PermissionListener
We will use `PermissionListener` for handling permission check result.
You will get result to `onPermissionGranted()` or `onPermissionDenied()` depending on approved permissions.

```java

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
```

#### -Start TedPermission 
TedPermission class requires `setPermissionListener()`, `setPermissions()`, and `check()` methods.
Call `check()` to start checking for permissions.

`setRationaleMessage()` and `setDeniedMessage()` are optional methods for displaying messages.

```java
    TedPermission.with(this)
    .setPermissionListener(permissionlistener)
    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
    .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
    .check();
```

<br/><br/>


### RxJava1
If you use RxJava1, You can use `request()` method instead `check()`.
When permission check has finished, you will receive `TedPermissionResult` instance.
`TedPermissionResult` instance has `isGranted()`, `getDeniedPermissions()` methods for checking permission check result.
```java

    TedRxPermission.with(this)
        .setDeniedMessage(
            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
        .request()
        .subscribe(tedPermissionResult -> {
          if (tedPermissionResult.isGranted()) {
            Toast.makeText(RxJava1Activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(RxJava1Activity.this,
                "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
                .show();
          }
        }, throwable -> {
        });

```

<br/><br/>


### RxJava2
RxJava2 api is very similiar to RxJava 1. You can use `request()` method to request for permissions like RxJava1.

```java
    TedRx2Permission.with(this)
        .setRationaleTitle(R.string.rationale_title)
        .setRationaleMessage(R.string.rationale_message) // "we need permission for read contact and find your location"
        .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
        .request()
        .subscribe(tedPermissionResult -> {
          if (tedPermissionResult.isGranted()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(this,
                "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
                .show();
          }
        }, throwable -> {
        });
```



<br/>

## Customize
TedPermission supports the following methods.<br />

* `setGotoSettingButton(boolean) (default: true)`
* `setRationaleTitle(R.string.xxx or String)`
* `setRationaleMessage(R.string.xxx or String)`
* `setRationaleConfirmText(R.string.xxx or String) (default: confirm / 확인)`
* `setDeniedTitle(R.string.xxx or String)`
* `setDeniedMessage(R.string.xxx or String)`
* `setDeniedCloseButtonText(R.string.xxx or String) (default: close / 닫기)`
* `setGotoSettingButtonText(R.string.xxx or String) (default: setting / 설정)`

Also you can use the following utility functions.
* `isGranted(Context context, String... permissions)`: Check if all permissions are granted
* `isDenied(Context context, String... permissions)`: Check if all permissions are denied
* `getDeniedPermissions(Context context, String... permissions)`
* `canRequestPermission(Activity activity, String... permissions)`: If `true` you can request a system popup, `false` means user checked  `Never ask again`.
* `startSettingActivityForResult(Activity activity)`
* `startSettingActivityForResult(Activity activity, int requestCode)`


<br/><br/>



## Number of Cases
1. Check permissions -> Already have permissions<br/>
: `onPermissionGranted()` is called.<br/>

2. Check permissions -> Don't have permissions<br/>
: Request dialog is shown.<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/request_dialog.png?raw=true)<br/>


3. Show request dialog -> User granted permissions<br/>
: `onPermissionGranted()` is called.<br/>

4. Show request dialog -> User denied one or more permissions<br/>
: Denied dialog is shown.<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/denied_dialog.png?raw=true)<br/>

5. Show denied dialog -> Close the dialog<br/>
: `onPermissionDenied()` called<br/>

6. Show denied dialog -> Setting button clicked<br/>
: `startActivityForResult()` to application Setting Activity.<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/setting_activity.png?raw=true)<br/>


7. Setting Activity -> `onActivityResult()`<br/>
: Check permissions again<br/>

8. Check permission -> Permissions are granted<br/>
: `onPermissionGranted()` is called.<br/>

9. Check permission -> There are denied permissions<br/>
: `onPermissionDenied()` is called.<br/>
 
<br/><br/>


![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/Screenshot_cases.png?raw=true)    


<br/><br/>


## License 
 ```code
Copyright 2017 Ted Park

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TedPermission-green.svg?style=true)](https://android-arsenal.com/details/1/3238)

# What is TedPermission?

After the update to Android 6.0 Marshmallow, we have to not only declare permissions in `AndroidManifest.xml`, but also request them at runtime. Furthermore, user can on/off permissions in application setting anytime. 
<br/>When you use **dangerous permissons**(ex. `CAMERA`, `READ_CONTACTS`, `READ_PHONE_STATE`, ...), you must check and request them runtime.<br/>
(http://developer.android.com/intl/ko/guide/topics/security/permissions.html#normal-dangerous)

You can make your own permission check logic [like this](http://developer.android.com/intl/ko/training/permissions/requesting.html), but  it's so complex and hard to use functions Google offers: `checkSelfPermission()`, `requestPermissions()`, `onRequestPermissionsResult()`, `onActivityResult()`.

TedPermission makes it easy to check and request android permissions.


(For Korean)
아래 블로그를 통해 마시멜로우 권한관련된 사항을 알아보세요
<br/>http://gun0912.tistory.com/55
<br/><br/>



## Demo

[![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/google_play_150.png?raw=true)](https://play.google.com/store/apps/details?id=com.gun0912.tedpermissiondemo)

<br/><br/>

![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/Screenshot.png?raw=true)    
           
           
1. Request Permissions.
2. If user denied permissions, we will show message dialog with Setting button.


## Setup


### Gradle

Edit `root/app/build.gradle` like below.

#### Normal
```gradle
dependencies {
    compile 'gun0912.ted:tedpermission:2.1.0'
}
```

#### RxJava1
```gradle
dependencies {
    compile 'gun0912.ted:tedpermission-rx1:2.1.0'
}
```

#### RxJava2
```gradle
dependencies {
    compile 'gun0912.ted:tedpermission-rx2:2.1.0'
}
```

If you think this library is useful, please press star button at upside.
<br/>
<img src="https://phaser.io/content/news/2015/09/10000-stars.png" width="200">

<br/><br/>

## How to use

### Normal
#### -Make PermissionListener
We will use PermissionListener for Permission Result.
You will get result to `onPermissionGranted()`, `onPermissionDenied()`

```java

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
```

#### -Start TedPermission 
TedPermission class requires `setPermissionListener()`, `setPermissions()`, and `check()`
`check()` will start to check permissions.

`setRationaleMessage()` and `setDeniedMessage()` are optional methods.

```java
    TedPermission.with(this)
    .setPermissionListener(permissionlistener)
    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
    .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
    .check();
```

<br/><br/>


### RxJava1
If you use RxJava1, You can use `request()` method instead `check()`
When Permission check finish, you can receive tedPermissionResult instance.
tedPermissionResult instance has `isGranted()`, `getDeniedPermissions()`
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
        }, () -> {
        });

```

<br/><br/>


### RxJava2
Also RxJava2 can use `request()` like RxJava1

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
        }, () -> {
        });
```



<br/>

## Customize
TedPermission support this method<br />

* `setGotoSettingButton(boolean) (default: true)`
* `setRationaleTitle(R.string.xxx or String)`
* `setRationaleMessage(R.string.xxx or String)`
* `setRationaleConfirmText(R.string.xxx or String) (default: confirm / 확인)`
* `setDeniedTitle(R.string.xxx or String)`
* `setDeniedMessage(R.string.xxx or String)`
* `setDeniedCloseButtonText(R.string.xxx or String) (default: close / 닫기)`
* `setGotoSettingButtonText(R.string.xxx or String) (default: setting / 설정)`

Also you can use util function.
* `isGranted(Context context, String... permissions)`: Check permissions all granted
* `isDenied(Context context, String... permissions)`: Check permissions all denied
* `getDeniedPermissions(Context context, String... permissions)`
* `canRequestPermission(Activity activity, String... permissions)`: If `true` you can request system popup, `false` mean user checked  `Never ask again`
* `startSettingActivityForResult(Activity activity)`
* `startSettingActivityForResult(Activity activity, int requestCode)`


<br/><br/>



## Number of Cases
1. Check permissions -> have permissions<br/>
: `onPermissionGranted()` called<br/>

2. Check permissions -> don't have permissions<br/>
: show request dialog<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/request_dialog.png?raw=true)<br/>


3. show request dialog -> granted permissions<br/>
: `onPermissionGranted()` called<br/>

4. show request dialog -> denied permissions<br/>
: show denied dialog<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/denied_dialog.png?raw=true)<br/>

5. show denied dialog -> close<br/>
: `onPermissionDenied()` called<br/>

6. show denied dialog -> setting<br/>
: `startActivityForResult()` to `setting` activity<br/>
![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/setting_activity.png?raw=true)<br/>


7. setting activity -> `onActivityResult()`<br/>
: check permission<br/>

8. check permission -> granted permissions<br/>
: `onPermissionGranted()` called<br/>

9. check permission -> denied permissions<br/>
: `onPermissionDenied()` called<br/>
 
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

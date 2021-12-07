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
- Edit `root/app/build.gradle` like below.
- You can choose only one library depend on your code style `normal`/`coroutine`/`rxJava2`/`rxJava3`
- Replace `x.y.z` with the version shown in the 'Maven Central' button below, or the specific version you want (e.g. replace `x.y.z` with `3.3.0` if you want v3.3.0).

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ParkSangGwon/tedpermission-normal.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ParkSangGwon%22%20AND%20a:%tedpermission-normal%22)

```gradle
repositories {
  google()
  mavenCentral()
}

dependencies {
    // Normal
    implementation 'io.github.ParkSangGwon:tedpermission-normal:x.y.z'
    
    // Coroutine
    implementation 'io.github.ParkSangGwon:tedpermission-coroutine:x.y.z'

    // RxJava2
    implementation 'io.github.ParkSangGwon:tedpermission-rx2:x.y.z'
    // RxJava3
    implementation 'io.github.ParkSangGwon:tedpermission-rx3:x.y.z'
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
You will get the result to `onPermissionGranted()` or `onPermissionDenied()` depending on approved permissions.

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
    TedPermission.create()
        .setPermissionListener(permissionlistener)
        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
        .check();
```

<br/><br/>

### Coroutine
If you use kotlin and coroutine, You can use `check()`function.
`TedPermissionResult` instance has `isGranted()`, `getDeniedPermissions()` methods for checking permission check result.
```kotlin
val permissionResult =
    TedPermission.create()
        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
        .check()
```
Also if you want know only granted result, you can use `checkGranted(): boolean`
<br/><br/>


### RxJava
If you use RxJava, You can use `request()` method instead `check()`.
When permission check has finished, you will receive `TedPermissionResult` instance.
`TedPermissionResult` instance has `isGranted()`, `getDeniedPermissions()` methods for checking permission check result.

```java
    TedPermission.create()
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
* `isGranted(String... permissions)`: Check if all permissions are granted
* `isDenied(String... permissions)`: Check if all permissions are denied
* `getDeniedPermissions(String... permissions)`
* `canRequestPermission(Activity activity, String... permissions)`: If `true` you can request a system popup, `false` means user checked  `Never ask again`.
* `startSettingActivityForResult()`
* `startSettingActivityForResult(int requestCode)`


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
Copyright 2021 Ted Park

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

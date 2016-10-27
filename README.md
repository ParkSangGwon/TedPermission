 [![Release](https://jitpack.io/v/ParkSangGwon/TedPermission.svg)](https://jitpack.io/ParkSangGwon/TedPermission)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TedPermission-green.svg?style=true)](https://android-arsenal.com/details/1/3238)

#What is TedPermission?

After Android Marshmallow, you have to not only decalare permisions in `AndroidManifest.xml` but also request permissions at runtime.<br/>
Furthermore anytime user can on/off permissions at application setting.<br/>
When you use dangerous permissons(ex. CAMERA, READ_CONTACTS, READ_PHONE_STATE), you have to check and request permissions runtime.<br/>
([See dangerous permissions](http://developer.android.com/intl/ko/guide/topics/security/permissions.html#normal-dangerous))<br/>

You can make check function yourself.<br/>
([How to Requesting Permissions at RunTime](http://developer.android.com/intl/ko/training/permissions/requesting.html))<br/>

But original check function is so complex and hard..<br/>
(`checkSelfPermission()`, `requestPermissions()`, `onRequestPermissionsResult()`, `onActivityResult()` ...)

TedPermission is simple permission check helper.


<br/><br/>



##Demo


![Screenshot](https://github.com/ParkSangGwon/TedPermission/blob/master/Screenshot.png?raw=true)    
           
           
1. Request Permissions.
2. If user denied permissions, we will show message dialog with Setting button.



<br/><br/>


##Setup


###Gradle

```javascript

dependencies {
    compile 'gun0912.ted:tedpermission:1.0.2'
}

```



<br/><br/>



##How to use


###1. Make PermissionListener
We will use PermissionListener for Permission Result.
You will get result to `onPermissionGranted()`, `onPermissionDenied()`

```javascript

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

<br/>
###2. Start TedPermission
TedPermission class need `setPermissionListener()`, `setPermissions()`.
and `check()` will start check permissions

`setRationaleMessage()`,`setDeniedMessage()` is optional method.

```javascript

    new TedPermission(this)
    .setPermissionListener(permissionlistener)
    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
    .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
    .check();

```




<br/>

##Proguard
If you use proguard, you have to add this code.
```javascript
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
````


<br/>

##Customize
You can customize something ...<br />


* `setGotoSettingButton(boolean) (default: true)`
* `setRationaleMessage(R.string.xxx or String)`
* `setRationaleConfirmText(R.string.xxx or String) (default: confirm / 확인)`
* `setDeniedMessage(R.string.xxx or String)`
* `setDeniedCloseButtonText(R.string.xxx or String) (default: close / 닫기)`
* `setGotoSettingButtonText(R.string.xxx or String) (default: setting / 설정)`



<br/><br/>



##Number of Cases
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


##Thanks 
* [Otto](https://github.com/square/otto) - An enhanced Guava-based event bus with emphasis on Android support




<br/><br/>


##License 
 ```code
Copyright 2016 Ted Park

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```

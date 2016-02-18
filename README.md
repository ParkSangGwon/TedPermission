 [![Release](https://jitpack.io/v/ParkSangGwon/TedPermission.svg)](https://jitpack.io/ParkSangGwon/TedPermission)

#What is TedPermission?

After Android Marshmallow, you have to not only decalare permisions in `AndroidManifest.xml` but request permissions at runtime.<br/>
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


#####Gradle
Get library from  [jitpack.io](https://jitpack.io/)
```javascript

repositories {
    
    maven { url "https://jitpack.io" }

}

dependencies {
    compile 'com.github.ParkSangGwon:TedPermission:v1.0.3'
}

```



<br/><br/>



##How to use


#####1. Make PermissionListener
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
#####2. Start TedPermission
TedPermission class need `setPermissionListener()`, `setDeniedMessage()`, `setPermissions()`.
and `check()` will start check permissions

```javascript

    new TedPermission(this)
    .setPermissionListener(permissionlistener)
    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
    .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
    .check();

```




<br/>


##Customize
You can customize something ...<br />

#####Function

* `setGotoSettingButton(boolean) (default: true)`

* `setDeniedCloseButtonText(R.string.xxx) (default: close / 닫기)`


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

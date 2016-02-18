package com.gun0912.tedpermission;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.gun0912.tedpermission.util.Dlog;
import com.gun0912.tedpermission.util.ObjectUtils;

/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedPermission {

    public static final String TAG="tedpark";

    private static TedInstance instance;





        public TedPermission(Context context){
            if(instance==null){
                instance = new TedInstance(context);
            }
        }


        public TedPermission setPermissionListener(PermissionListener listener){
            instance.listener=listener;

            return this;
        }


        public TedPermission setPermissions( String... permissions){
            instance.permissions=permissions;
            return this;
        }

        public TedPermission setDeniedMessage(String denyMessage){
            instance.denyMessage =denyMessage;
            return this;
        }


        public void check(){



            if(instance==null){
                throw new NullPointerException("TedInstance is null on TedPermission");
            }
            else if (instance.context == null) {
                throw new NullPointerException("Context is null on TedPermission");
            }
            else if(instance.listener==null){
                throw new NullPointerException("PermissionListener is null on TedPermission");
            }
            else if(ObjectUtils.isEmpty(instance.permissions)){
                throw new NullPointerException("permissions is empty on TedPermission");
            }
            else  if (TextUtils.isEmpty(instance.denyMessage)) {
                throw new NullPointerException("denyMessage is empty on TedPermission");
            }




            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                Dlog.d("preMarshmallow");
                instance.listener.onPermissionGranted();

            }else{
                Dlog.d("Marshmallow");
                instance.checkPermissions();
            }



        }








}

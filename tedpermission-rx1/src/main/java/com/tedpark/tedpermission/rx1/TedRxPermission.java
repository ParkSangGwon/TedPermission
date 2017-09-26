package com.tedpark.tedpermission.rx1;

import android.content.Context;

import com.gun0912.tedpermission.PermissionBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionBase;
import com.gun0912.tedpermission.TedPermissionResult;

import java.util.ArrayList;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;


public class TedRxPermission extends TedPermissionBase {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends PermissionBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public Observable<TedPermissionResult> request() {
            return Observable.create(new OnSubscribe<TedPermissionResult>() {
                @Override
                public void call(final Subscriber<? super TedPermissionResult> emitter) {
                    PermissionListener listener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            emitter.onNext(new TedPermissionResult(null));
                            emitter.onCompleted();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            emitter.onNext(new TedPermissionResult(deniedPermissions));
                            emitter.onCompleted();
                        }
                    };

                    try {
                        setPermissionListener(listener);
                        checkPermissions();
                    } catch (Exception exception) {
                        emitter.onError(exception);
                    }
                }
            });
        }
    }


}

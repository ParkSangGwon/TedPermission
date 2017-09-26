package com.tedpark.tedpermission.rx2;

import android.content.Context;

import com.gun0912.tedpermission.PermissionBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionBase;
import com.gun0912.tedpermission.TedPermissionResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class TedRx2Permission extends TedPermissionBase {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends PermissionBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public Observable<TedPermissionResult> request() {
            return Observable.create(new ObservableOnSubscribe<TedPermissionResult>() {
                @Override
                public void subscribe(@NonNull final ObservableEmitter<TedPermissionResult> emitter) throws Exception {

                    PermissionListener listener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            emitter.onNext(new TedPermissionResult(null));
                            emitter.onComplete();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            emitter.onNext(new TedPermissionResult(deniedPermissions));
                            emitter.onComplete();
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

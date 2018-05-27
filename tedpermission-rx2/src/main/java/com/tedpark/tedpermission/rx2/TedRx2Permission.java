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
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class TedRx2Permission extends TedPermissionBase {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends PermissionBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public Single<TedPermissionResult> request() {
            return Single.create(new SingleOnSubscribe<TedPermissionResult>() {
                @Override
                public void subscribe(final SingleEmitter<TedPermissionResult> emitter) throws Exception {
                    PermissionListener listener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            emitter.onSuccess(new TedPermissionResult(null));
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            emitter.onSuccess(new TedPermissionResult(deniedPermissions));
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

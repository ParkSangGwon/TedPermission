package com.gun0912.tedpermission.rx3;

import com.gun0912.tedpermission.PermissionBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionResult;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;


public class TedPermission {

    public static Builder create() {
        return new Builder();
    }

    public static class Builder extends PermissionBuilder<Builder> {

        public Single<TedPermissionResult> request() {
            return Single.create(new SingleOnSubscribe<TedPermissionResult>() {
                @Override
                public void subscribe(@NonNull final SingleEmitter<TedPermissionResult> emitter) {
                    PermissionListener listener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            emitter.onSuccess(new TedPermissionResult(null));
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
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

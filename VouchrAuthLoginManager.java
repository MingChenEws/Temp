package com.zellepay.zdk.ux;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.surpriise.vouchrcommon.engine.LoginInfo;
import com.surpriise.vouchrcommon.engine.obj.Credential;
import com.surpriise.vouchrcommon.engine.obj.SimpleUser;
import com.surpriise.vouchrcommon.engine.v3.interfaces.AuthLoginManager;

import rx.Observable;

public class VouchrAuthLoginManager implements AuthLoginManager {
    public static final String NETWORK_NAME = "ZELLEDEV_JWT";

    @Override
    public Observable<Credential> login(Context context) {
        return Observable.just(new Credential(NETWORK_NAME, "JWTToken"));
    }

    @Override
    @WorkerThread
    public Credential refreshToken(Context context) {
        return new Credential(NETWORK_NAME, "JWTToken");
    }

    @Override
    public Observable<SimpleUser> register(Context context, @Nullable String email) {
        return Observable.just(SimpleUser.Builder.init("firstName", "lastName", "email").create());
    }

    @Override
    public void logout(Context context) {}

    @Override
    public LoginInfo getNetworkInfo() {
        return new LoginInfo(NETWORK_NAME, true, "Simple Network");
    }
}

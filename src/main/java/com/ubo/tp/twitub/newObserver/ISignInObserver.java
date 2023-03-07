package com.ubo.tp.twitub.newObserver;

public interface ISignInObserver {

    void login(String username, String password);

    void wrongCredentials();
}

package com.ubo.tp.twitub.observer;

public interface ISignInObserver {

    void doLogin(String identifiant, String password);

    void doRegister();
}

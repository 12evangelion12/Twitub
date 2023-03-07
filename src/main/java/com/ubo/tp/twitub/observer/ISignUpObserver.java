package com.ubo.tp.twitub.observer;

public interface ISignUpObserver {

    void doRegister(String username, String pseudonyme, String password);

    void cancelRegister();
}

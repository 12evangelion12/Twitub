package com.ubo.tp.twitub.observer;

public interface ISignInObserver {

    default void login(String username, String password) {
    }

    default void wrongCredentials() {
    }

    default void doRegister() {
    }
}

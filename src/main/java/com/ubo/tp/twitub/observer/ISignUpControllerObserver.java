package com.ubo.tp.twitub.observer;

public interface ISignUpControllerObserver {

    default void register(String usertag, String username, String password) {

    }

    default void cancelRegister() {
    }

    default void usertagAlreadyUse() {
    }

    default void credentialNotSpecified() {
    }

    default void registerSuccess() {
    }

    default void usernameAlreadyExist() {
    }
}

package main.java.com.ubo.tp.twitub.newObserver;

public interface ISignUpControllerObserver {

    void register(String usertag, String username, String password);

    default void cancelRegister() {
    }

    default void usertagAlreadyUse() {
    }

    default void credentialNotSpecified() {
    }

    default void registerSuccess() {
    }
}

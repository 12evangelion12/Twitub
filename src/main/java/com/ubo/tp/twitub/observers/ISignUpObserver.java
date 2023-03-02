package main.java.com.ubo.tp.twitub.observers;

public interface ISignUpObserver {

    void doRegister(String username, String pseudonyme, String password);

    void cancelRegister();
}

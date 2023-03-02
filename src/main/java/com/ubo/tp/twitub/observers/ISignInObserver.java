package main.java.com.ubo.tp.twitub.observers;

public interface ISignInObserver {

    void doLogin(String identifiant, String password);

    void doRegister();
}

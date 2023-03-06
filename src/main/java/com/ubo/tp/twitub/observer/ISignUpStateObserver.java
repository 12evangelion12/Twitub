package main.java.com.ubo.tp.twitub.observer;

import main.java.com.ubo.tp.twitub.datamodel.User;

public interface ISignUpStateObserver {

    void fieldNotSpecified();

    void usertagAlreadyExist(String usertag);

    void registerSuccess(User user);
}

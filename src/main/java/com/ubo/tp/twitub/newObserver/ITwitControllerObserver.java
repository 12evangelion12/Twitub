package main.java.com.ubo.tp.twitub.newObserver;

import main.java.com.ubo.tp.twitub.datamodel.User;

public interface ITwitControllerObserver {

    void sendTwit(User user, String twitMessage);
}

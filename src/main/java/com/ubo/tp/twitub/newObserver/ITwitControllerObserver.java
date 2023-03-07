package com.ubo.tp.twitub.newObserver;


import com.ubo.tp.twitub.datamodel.User;

public interface ITwitControllerObserver {

    void sendTwit(User user, String twitMessage);
}

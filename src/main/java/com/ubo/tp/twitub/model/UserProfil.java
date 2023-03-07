package com.ubo.tp.twitub.model;

import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.newObserver.IUserObserver;

import java.util.ArrayList;
import java.util.List;

public class UserProfil {

    private final List<IUserObserver> userProfilChangeObservers;
    private User user;
    private int TwitCount;

    public UserProfil() {
        userProfilChangeObservers = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTwitCount() {
        return TwitCount;
    }

    public void setTwitCount(int twitCount) {
        TwitCount = twitCount;
        userProfilChangeObservers.forEach(observer -> observer.notifyTwitCountChanged(twitCount));
    }

    public void addObserver(IUserObserver observer) {
        userProfilChangeObservers.add(observer);
    }
}
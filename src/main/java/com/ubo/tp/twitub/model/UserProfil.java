package com.ubo.tp.twitub.model;


import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.observer.IUserProfilChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class UserProfil {

    private User user;
    private int TwitCount;
    private List<IUserProfilChangeObserver> userProfilChangeObservers;

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

    public void addObserver(IUserProfilChangeObserver observer) {
        userProfilChangeObservers.add(observer);
    }
}
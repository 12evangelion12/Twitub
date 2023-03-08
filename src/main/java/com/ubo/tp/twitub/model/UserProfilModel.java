package com.ubo.tp.twitub.model;

import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.observer.IUserObserver;

import java.util.ArrayList;
import java.util.List;

public class UserProfilModel {

    private final List<IUserObserver> userProfilChangeObservers;
    private User session;
    private int TwitCount;
    private List<User> followers;

    public UserProfilModel() {
        userProfilChangeObservers = new ArrayList<>();
    }

    public User getSession() {
        return session;
    }

    public void setSession(User session) {
        this.session = session;
        userProfilChangeObservers.forEach(observer -> observer.updateFollowerList(session));
    }

    public int getTwitCount() {
        return TwitCount;
    }

    public void setTwitCount(int twitCount) {
        TwitCount = twitCount;
        userProfilChangeObservers.forEach(observer -> observer.notifyTwitCountChanged(twitCount));
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void addObserver(IUserObserver observer) {
        userProfilChangeObservers.add(observer);
    }
}
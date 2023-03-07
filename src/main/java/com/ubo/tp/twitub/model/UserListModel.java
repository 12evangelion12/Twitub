package com.ubo.tp.twitub.model;

import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.newObserver.IUserObserver;

import java.util.ArrayList;
import java.util.List;

public class UserListModel {

    private final List<IUserObserver> userObservers;
    private List<User> users;
    private User session;

    public UserListModel(User session, List<User> users) {
        userObservers = new ArrayList<>();
        this.users = users;
        this.session = session;
    }

    public User getSession() {
        return session;
    }

    public void setSession(User session) {
        this.session = session;
        userObservers.forEach(observer -> observer.updateUserList(session));
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addObserver(IUserObserver observer) {
        userObservers.add(observer);
    }
}

package main.java.com.ubo.tp.twitub.model;

import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.observer.IUserObserver;

import java.util.ArrayList;
import java.util.List;

public class UserListModel {

    User session;
    List<User> users;
    List<IUserObserver> userObservers;

    public UserListModel(User session) {
        this.session = session;
        users = new ArrayList<>();
        userObservers = new ArrayList<>();
    }

    public User getSession() {
        return session;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        userObservers.forEach(IUserObserver::notifyUserChanged);
    }

    public void addObserver(IUserObserver observer) {
        userObservers.add(observer);
    }
}

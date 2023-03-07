package com.ubo.tp.twitub.ihm.user;


import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.observer.IAccountObserver;
import com.ubo.tp.twitub.observer.IUserFollowObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersPageController implements IPage.IController, IUserFollowObserver {

    private final List<IAccountObserver> accountObserverList;
    private final UsersPageView usersPageView;
    private final IDatabase database;
    private final EntityManager mEntityManager;
    private final User session;

    public UsersPageController(User session, IDatabase database, EntityManager mEntityManager) {
        accountObserverList = new ArrayList<>();
        this.database = database;
        this.mEntityManager = mEntityManager;
        this.session = session;
        usersPageView = new UsersPageView(getUsers(), session);
        usersPageView.addFollowUserObserver(this);
    }

    private List<User> getUsers() {

        return database.getUsers().stream().filter(
                user1 -> !session.getUserTag().equalsIgnoreCase(user1.getUserTag())).collect(Collectors.toList());
    }

    @Override
    public void init() {
        usersPageView.initUIComponents();
    }

    @Override
    public Component show() {
        return usersPageView.show();
    }

    @Override
    public void addObserver(IAccountObserver observer) {
        accountObserverList.add(observer);
    }

    @Override
    public void notifyUserFollow(User userFollowed) {
        session.addFollowing(userFollowed.getUserTag());
        mEntityManager.sendUser(session);
    }

    @Override
    public void notifyUserUnfollow(User userUnfollowed) {
        session.removeFollowing(userUnfollowed.getUserTag());
        mEntityManager.sendUser(session);
    }
}

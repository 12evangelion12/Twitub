package com.ubo.tp.twitub.ihm.user;

import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.UserListModel;
import com.ubo.tp.twitub.observer.IUserObserver;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class UsersPageController implements IPage.IController, IDatabaseObserver, IUserObserver {
    private final UsersPageView usersPageView;
    private final IDatabase database;
    private final EntityManager mEntityManager;
    private final UserListModel userListModel;

    public UsersPageController(User session, IDatabase database, EntityManager mEntityManager) {
        this.database = database;
        this.database.addObserver(this);
        this.mEntityManager = mEntityManager;
        userListModel = new UserListModel(session, getUsers(session));
        usersPageView = new UsersPageView(userListModel);
        usersPageView.addObserver(this);
    }

    private List<User> getUsers(User session) {

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
    public void notifyUserFollow(User selectedUser) {
        User session = userListModel.getSession();
        session.addFollowing(selectedUser.getUserTag());
        mEntityManager.sendUser(session);
    }

    @Override
    public void notifyUserUnfollow(User selectedUser) {
        User session = userListModel.getSession();
        session.removeFollowing(selectedUser.getUserTag());
        mEntityManager.sendUser(session);
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        if (userListModel.getSession().getUserTag().equalsIgnoreCase(modifiedUser.getUserTag())) {
            userListModel.setSession(modifiedUser);
        }
    }
}

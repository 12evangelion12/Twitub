package com.ubo.tp.twitub.ihm.profil;

import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.UserProfilModel;
import com.ubo.tp.twitub.observer.IAccountObserver;
import com.ubo.tp.twitub.observer.IUserObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfilPageController implements IPage.IController, IDatabaseObserver, IUserObserver {

    private final List<IAccountObserver> accountObserverList;
    private final ProfilPageView profilPageView;
    private final IDatabase database;
    private final EntityManager entityManager;
    private UserProfilModel userProfilModel;

    public ProfilPageController(User user, IDatabase database, EntityManager entityManager) {
        accountObserverList = new ArrayList<>();
        this.database = database;
        this.entityManager = entityManager;
        database.addObserver(this);
        initUserProfil(user);
        profilPageView = new ProfilPageView(userProfilModel);
        profilPageView.addFollowUserObserver(this);
    }

    private void updateTwitCounter(Twit modifiedTwit) {
        if (profilPageView != null && modifiedTwit.getTwiter().getUserTag().equalsIgnoreCase(userProfilModel.getSession().getUserTag())) {
            Set<Twit> twits = database.getTwits();
            int userTwitCount = (int) twits.stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(userProfilModel.getSession().getUserTag())).count();
            profilPageView.updateUserTwitCount(userTwitCount);
        }
    }

    private void initUserProfil(User session) {

        Set<Twit> twits = database.getTwits();
        int userTwitCount = (int) twits.stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())).count();

        userProfilModel = new UserProfilModel();
        userProfilModel.setSession(session);
        userProfilModel.setTwitCount(userTwitCount);
        userProfilModel.setFollowers(getUserFollowers(session));
        userProfilModel.addObserver(this);
    }

    private List<User> getUserFollowers(User session) {
        return database.getUsers().stream().filter(user -> session.getFollows().contains(user.getUserTag())).collect(Collectors.toList());
    }

    @Override
    public void init() {
        profilPageView.initUIComponents();
        database.addObserver(this);
    }

    @Override
    public Component show() {
        return profilPageView.show();
    }

    @Override
    public void addObserver(IAccountObserver observer) {
        accountObserverList.add(observer);
    }

    @Override
    public void notifyTwitCountChanged(int count) {
        profilPageView.updateUserTwitCount(count);
    }

    @Override
    public void notifyUserFollow(User userFollowed) {
        User session = userProfilModel.getSession();
        session.addFollowing(userFollowed.getUserTag());
        entityManager.sendUser(session);
    }

    @Override
    public void notifyUserUnfollow(User userUnfollowed) {
        User session = userProfilModel.getSession();
        session.removeFollowing(userUnfollowed.getUserTag());
        entityManager.sendUser(session);
    }

    @Override
    public void notifyTwitAdded(Twit addedTwit) {
        updateTwitCounter(addedTwit);
    }

    @Override
    public void notifyTwitDeleted(Twit deletedTwit) {
        updateTwitCounter(deletedTwit);
    }

    @Override
    public void notifyUserModified(User modifiedUser) {

        if (modifiedUser.getUserTag().equalsIgnoreCase(userProfilModel.getSession().getUserTag())) {
            userProfilModel.setFollowers(getUserFollowers(modifiedUser));
            userProfilModel.setSession(modifiedUser);
        }
    }
}

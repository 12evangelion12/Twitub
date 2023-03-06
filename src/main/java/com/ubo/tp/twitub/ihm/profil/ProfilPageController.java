package main.java.com.ubo.tp.twitub.ihm.profil;

import main.java.com.ubo.tp.twitub.core.EntityManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.Twit;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.model.UserProfil;
import main.java.com.ubo.tp.twitub.observer.IAccountObserver;
import main.java.com.ubo.tp.twitub.observer.IUserFollowObserver;
import main.java.com.ubo.tp.twitub.observer.IUserProfilChangeObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfilPageController implements IPage.IController, IUserProfilChangeObserver, IUserFollowObserver {

    private final List<IAccountObserver> accountObserverList;
    private final ProfilPageView profilPageView;
    private final IDatabase database;
    private final EntityManager entityManager;
    private final User session;

    public ProfilPageController(User user, IDatabase database, EntityManager entityManager) {
        accountObserverList = new ArrayList<>();
        this.database = database;
        this.entityManager = entityManager;
        this.session = user;
        profilPageView = new ProfilPageView(initUserProfil(), getUserFollowers());
        profilPageView.addFollowUserObserver(this);
    }

    private UserProfil initUserProfil() {

        Set<Twit> twits = database.getTwits();
        int userTwitCount = (int) twits.stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())).count();

        UserProfil userProfil = new UserProfil();
        userProfil.setUser(session);
        userProfil.setTwitCount(userTwitCount);
        userProfil.addObserver(this);

        return userProfil;
    }

    private List<User> getUserFollowers() {
        return database.getUsers().stream().filter(user -> session.getFollows().contains(user.getUserTag())).collect(Collectors.toList());
    }

    @Override
    public void init() {
        profilPageView.initUIComponents();
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
        session.addFollowing(userFollowed.getUserTag());
        entityManager.sendUser(session);
        profilPageView.updateListFollowers(getUserFollowers());
    }

    @Override
    public void notifyUserUnfollow(User userUnfollowed) {
        session.removeFollowing(userUnfollowed.getUserTag());
        entityManager.sendUser(session);
        profilPageView.updateListFollowers(getUserFollowers());
    }
}

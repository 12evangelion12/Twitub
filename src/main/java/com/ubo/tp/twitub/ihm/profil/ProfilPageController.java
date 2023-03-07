package com.ubo.tp.twitub.ihm.profil;

public class ProfilPageController {
/*
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

    @Override
    public void notifyTwitAdded(Twit addedTwit) {
        if (profilPageView != null && addedTwit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())) {
            Set<Twit> twits = database.getTwits();
            int userTwitCount = (int) twits.stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())).count();
            profilPageView.updateUserTwitCount(userTwitCount);
        }
    }

    @Override
    public void notifyTwitDeleted(Twit deletedTwit) {
        if (profilPageView != null && deletedTwit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())) {
            Set<Twit> twits = database.getTwits();
            int userTwitCount = (int) twits.stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(session.getUserTag())).count();
            profilPageView.updateUserTwitCount(userTwitCount);
        }
    }*/
}

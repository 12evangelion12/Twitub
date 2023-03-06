package main.java.com.ubo.tp.twitub.observer;

import main.java.com.ubo.tp.twitub.datamodel.User;

public interface IAccountObserver {

    void notifyUserConnection(User user);

    void notifyUserDisconnection();

    void notifyUserInscription();

    void notifyShowTwitPage(User user);

    void notifyShowFollowersPage(User user);

    void notifyShowProfilPage(User user);
}

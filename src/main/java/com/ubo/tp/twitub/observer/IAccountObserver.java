package com.ubo.tp.twitub.observer;

import com.ubo.tp.twitub.datamodel.User;

public interface IAccountObserver {

    void notifyUserConnection(User user);

    void notifyUserDisconnection();

    void notifyUserInscription();

    void notifyShowTwitPage();

    void notifyShowFollowersPage();

    void notifyShowProfilPage();
}

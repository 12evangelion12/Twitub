package com.ubo.tp.twitub.newObserver;

import com.ubo.tp.twitub.datamodel.User;

public interface IAccountObserver {

    void notifyUserConnection(User user);

    void notifyUserDisconnection();

    void notifyUserInscription();

    void notifyShowTwitPage(User user);

    void notifyShowFollowersPage(User user);

    void notifyShowProfilPage(User user);
}

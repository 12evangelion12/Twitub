package main.java.com.ubo.tp.twitub.observers;

import main.java.com.ubo.tp.twitub.datamodel.User;

public interface IAccountObserver {

    void notifyUserConnection(User user);

    void notifyUserDisconnection();

    void notifyUserInscription();
}

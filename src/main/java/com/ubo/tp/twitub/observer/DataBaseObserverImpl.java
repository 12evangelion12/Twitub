package com.ubo.tp.twitub.observer;

import com.ubo.tp.twitub.TwitubLauncher;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.datamodel.User;

import java.util.logging.Level;


public class DataBaseObserverImpl implements IDatabaseObserver {

    @Override
    public void notifyTwitAdded(Twit addedTwit) {

        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Un twit à été ajouté par : {0}", addedTwit.getTwiter().getName());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "contenu du nouveau twit : \"{0}\"\n", addedTwit.getText());
    }

    @Override
    public void notifyTwitDeleted(Twit deletedTwit) {

        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Un twit à été supprimé par : {0}", deletedTwit.getTwiter().getName());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "contenu du twit supprimé : \"{0}\"\n", deletedTwit.getText());
    }

    @Override
    public void notifyTwitModified(Twit modifiedTwit) {

        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Un twit à été modifié par : {0}", modifiedTwit.getTwiter().getName());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "contenu du twit modifié : \"{0}\"\n", modifiedTwit.getText());
    }

    @Override
    public void notifyUserAdded(User addedUser) {

        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Un utilisateur à été ajouté !");
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "UserTag: {0}", addedUser.getUserTag());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "UserUUID: {0}", addedUser.getUuid());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "UserName: {0}", addedUser.getName());
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Password: {0}\n", addedUser.getUserPassword());
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

        TwitubLauncher.getLogger(getClass()).log(Level.FINE, "L'utilisateur \"{0}\" a été supprimé !\n", deletedUser.getName());
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        TwitubLauncher.getLogger(getClass()).log(Level.FINE, "L'utilisateur \"{0}\" a été modifié !\n", modifiedUser.getName());
    }
}

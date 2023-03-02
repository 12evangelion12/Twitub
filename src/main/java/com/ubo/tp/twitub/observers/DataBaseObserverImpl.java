package main.java.com.ubo.tp.twitub.observers;

import main.java.com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import main.java.com.ubo.tp.twitub.datamodel.Twit;
import main.java.com.ubo.tp.twitub.datamodel.User;

public class DataBaseObserverImpl implements IDatabaseObserver {

    @Override
    public void notifyTwitAdded(Twit addedTwit) {

        System.out.println("\nUn twit à été ajouté par : "+addedTwit.getTwiter().getName());
        System.out.println("contenu du nouveau twit : \""+ addedTwit.getText()+ "\"");
    }

    @Override
    public void notifyTwitDeleted(Twit deletedTwit) {

        System.out.println("\nUn twit à été supprimé par : "+deletedTwit.getTwiter().getName());
        System.out.println("contenu du twit supprimé : \""+ deletedTwit.getText()+ "\"");
    }

    @Override
    public void notifyTwitModified(Twit modifiedTwit) {

        System.out.println("\nUn twit à été modifié par :"+modifiedTwit.getTwiter().getName());
        System.out.println("contenu du twit modifié : \""+ modifiedTwit.getText()+ "\"");
    }

    @Override
    public void notifyUserAdded(User addedUser) {

        System.out.println("\nUn utilisateur à été ajouté !");
        System.out.println("UserTag: "+ addedUser.getUserTag());
        System.out.println("UserUUID: "+addedUser.getUuid());
        System.out.println("UserName: "+addedUser.getName());
        System.out.println("Password: "+addedUser.getUserPassword());
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

        System.out.println("L'utilisateur \""+deletedUser.getName()+"\" a été supprimé !");
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        System.out.println("L'utilisateur \""+modifiedUser.getName()+"\" a été modifié !");
    }
}

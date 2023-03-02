package main.java.com.ubo.tp.twitub.ihm.twit;

import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.Twit;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.observers.IAccountObserver;
import main.java.com.ubo.tp.twitub.observers.ISignOutObserver;
import main.java.com.ubo.tp.twitub.observers.ITwitSendObserver;
import main.java.com.ubo.tp.twitub.observers.ITwitStateObserver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TwitPageController implements IPage.IController, ISignOutObserver, ITwitSendObserver {

    private final TwitPageView twitPageView;
    private final List<IAccountObserver> accountObservers;
    private final List<ITwitStateObserver> twitStateObservers;
    private final IDatabase database;
    private final User user;

    public TwitPageController(IDatabase database, User user) {
        twitPageView = new TwitPageView(user.getName());
        accountObservers = new ArrayList<>();
        twitStateObservers = new ArrayList<>();
        twitStateObservers.add(twitPageView);
        this.database = database;
        this.user = user;
    }

    @Override
    public void init() {
        twitPageView.initUIComponents();
        twitPageView.addLogoutObserver(this);
        twitPageView.addTwitObserver(this);
    }

    @Override
    public JPanel show() {
        return twitPageView.show();
    }

    @Override
    public void addObserver(IAccountObserver observer) {
        accountObservers.add(observer);
    }

    @Override
    public void doLogout() {
        accountObservers.forEach(IAccountObserver::notifyUserDisconnection);
    }

    @Override
    public void sendTwit(String twitString) {

        if(twitString.length() > 250) {
            twitStateObservers.forEach(ITwitStateObserver::twitTooLong);
            return;
        }

        Twit twit = new Twit(user, twitString);
        database.addTwit(twit);
        twitStateObservers.forEach(ITwitStateObserver::twitAccepted);
    }
}

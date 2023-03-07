package com.ubo.tp.twitub.ihm.twit;


import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.TwitListModel;
import com.ubo.tp.twitub.newObserver.ITwitControllerObserver;
import com.ubo.tp.twitub.observer.IAccountObserver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TwitPageController implements IPage.IController, IDatabaseObserver, ITwitControllerObserver {

    private final TwitPageView twitPageView;
    private final TwitListModel twitListModel;
    private final List<IAccountObserver> accountObservers;
    private final EntityManager entityManager;

    public TwitPageController(User user, IDatabase database, EntityManager entityManager) {
        twitListModel = new TwitListModel();
        twitListModel.setTwits(new ArrayList<>(database.getTwits()));
        twitPageView = new TwitPageView(user, twitListModel);
        twitPageView.addController(this);
        accountObservers = new ArrayList<>();
        database.addObserver(this);
        this.entityManager = entityManager;
    }

    @Override
    public void init() {
        twitPageView.initUIComponents();
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
    public void notifyTwitAdded(Twit addedTwit) {
        List<Twit> twits = twitListModel.getTwits();
        twits.add(addedTwit);
        twitListModel.setTwits(twits);
    }

    @Override
    public void sendTwit(User user, String twitMessage) {
        //entityManager.sendTwit();
    }

    /*if (twitString.length() > 250) {
            twitStateObservers.forEach(ITwitStateObserver::twitTooLong);
            return;
        }

        Twit twit = new Twit(user, twitString);
        entityManager.sendTwit(twit);
        twitStateObservers.forEach(ITwitStateObserver::twitAccepted);*/
}

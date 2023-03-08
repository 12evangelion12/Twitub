package com.ubo.tp.twitub.ihm.twit;

import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.TwitListModel;
import com.ubo.tp.twitub.newObserver.IAccountObserver;
import com.ubo.tp.twitub.newObserver.ITwitObserver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwitPageController implements IPage.IController, IDatabaseObserver, ITwitObserver {

    private final TwitPageView twitPageView;
    private final TwitListModel twitListModel;
    private final List<IAccountObserver> accountObservers;
    private final EntityManager entityManager;
    private final IDatabase database;

    public TwitPageController(User user, IDatabase database, EntityManager entityManager) {
        twitListModel = new TwitListModel();
        twitListModel.setTwits(new ArrayList<>(database.getTwits()));
        twitPageView = new TwitPageView(user, twitListModel);
        twitPageView.addController(this);
        accountObservers = new ArrayList<>();
        database.addObserver(this);
        this.entityManager = entityManager;
        this.database = database;
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

        Twit twit = new Twit(user, twitMessage);
        entityManager.sendTwit(twit);
    }

    @Override
    public void searchTwit(String twitMessage) {

        System.out.println(twitMessage);

        List<Twit> twits = new ArrayList<>();
        if (twitMessage.isEmpty()) {
            twits.addAll(database.getTwits());
        } else if (twitMessage.startsWith("@")) {
            twits.addAll(database.getTwitsWithUserTag(twitMessage.replaceFirst("@", "")));
            twits.addAll(database.getTwits().stream().filter(twit -> twit.getTwiter().getUserTag().equalsIgnoreCase(twitMessage.replaceFirst("@", ""))).collect(Collectors.toList()));
        } else if (twitMessage.contains("#")) {
            twits.addAll(database.getTwitsWithTag(twitMessage.replaceFirst("#", "")));
        } else {
            twits.addAll(database.getTwits().stream().filter(twit -> twit.getText().contains(twitMessage)).collect(Collectors.toList()));
        }
        twitListModel.setTwits(twits);
    }
}

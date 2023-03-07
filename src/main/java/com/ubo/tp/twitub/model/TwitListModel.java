package main.java.com.ubo.tp.twitub.model;

import main.java.com.ubo.tp.twitub.datamodel.Twit;
import main.java.com.ubo.tp.twitub.newObserver.ITwitListModelObserver;

import java.util.ArrayList;
import java.util.List;

public class TwitListModel {

    private final List<ITwitListModelObserver> twitListModelObservers;
    private List<Twit> twits;

    public TwitListModel() {
        twitListModelObservers = new ArrayList<>();
    }

    public List<Twit> getTwits() {
        return twits;
    }

    public void setTwits(List<Twit> twits) {
        this.twits = twits;
        twitListModelObservers.forEach(observer -> observer.notifyTwitListChanged(twits));
    }

    public void addObserver(ITwitListModelObserver observer) {
        twitListModelObservers.add(observer);
    }
}

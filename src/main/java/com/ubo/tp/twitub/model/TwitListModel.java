package com.ubo.tp.twitub.model;

import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.observer.ITwitObserver;

import java.util.ArrayList;
import java.util.List;

public class TwitListModel {

    private final List<ITwitObserver> twitListModelObservers;
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

    public void addObserver(ITwitObserver observer) {
        twitListModelObservers.add(observer);
    }
}

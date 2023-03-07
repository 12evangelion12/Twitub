package main.java.com.ubo.tp.twitub.newObserver;

import main.java.com.ubo.tp.twitub.datamodel.Twit;

import java.util.List;

public interface ITwitListModelObserver {

    void notifyTwitListChanged(List<Twit> twits);
}

package com.ubo.tp.twitub.observer;

import com.ubo.tp.twitub.datamodel.Twit;
import com.ubo.tp.twitub.datamodel.User;

import java.util.List;

public interface ITwitObserver {

    default void sendTwit(User user, String twitMessage) {
    }

    default void searchTwit(String twitMessage) {
    }

    default void notifyTwitListChanged(List<Twit> twits) {
    }

    default void notifyTwitIsTooLong() {
    }

    default void notifyTwitIsEmpty() {
    }

    default void notifyTwitAccepted() {
    }

    default void notifyTwitAdded() {
    }
}

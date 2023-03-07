package main.java.com.ubo.tp.twitub.newObserver;

public interface ITwitSendComponentObserver {

    default void notifyTwitIsTooLong() {
    }

    default void notifyTwitIsEmpty() {
    }

    default void notifyTwitAccepted() {
    }
}

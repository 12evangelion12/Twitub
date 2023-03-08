package com.ubo.tp.twitub.observer;

import com.ubo.tp.twitub.datamodel.User;

public interface IUserObserver {
    default void notifyUserFollow(User selectedUser) {
    }

    default void notifyUserUnfollow(User selectedUser) {
    }

    default void notifyTwitCountChanged(int twitCount) {
    }

    default void notifySearchUserButtonClicked() {
    }

    default void updateUserList(User session) {
    }

    default void updateFollowerList(User session) {
    }
}

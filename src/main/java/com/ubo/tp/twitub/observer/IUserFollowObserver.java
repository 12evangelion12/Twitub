package main.java.com.ubo.tp.twitub.observer;

import main.java.com.ubo.tp.twitub.datamodel.User;

public interface IUserFollowObserver {

    void notifyUserFollow(User userFollowed);

    void notifyUserUnfollow(User userUnfollowed);
}

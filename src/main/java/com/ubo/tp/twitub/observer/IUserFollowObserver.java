package com.ubo.tp.twitub.observer;


import com.ubo.tp.twitub.datamodel.User;

public interface IUserFollowObserver {

    void notifyUserFollow(User userFollowed);

    void notifyUserUnfollow(User userUnfollowed);
}

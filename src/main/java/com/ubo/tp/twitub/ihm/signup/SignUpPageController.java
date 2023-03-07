package com.ubo.tp.twitub.ihm.signup;


import com.ubo.tp.twitub.common.ResourceManager;
import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.observer.IAccountObserver;
import com.ubo.tp.twitub.observer.ISignUpObserver;
import com.ubo.tp.twitub.observer.ISignUpStateObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SignUpPageController implements IPage.IController, ISignUpObserver {

    private final IDatabase database;
    private final EntityManager entityManager;
    private final SignUpPageView signUpPageView;
    private final List<IAccountObserver> accountObservers;
    private final List<ISignUpStateObserver> signUpStateObservers;

    public SignUpPageController(IDatabase database, EntityManager entityManager) {
        this.database = database;
        this.entityManager = entityManager;
        signUpPageView = new SignUpPageView();
        accountObservers = new ArrayList<>();
        signUpStateObservers = new ArrayList<>();
    }

    @Override
    public void init() {
        signUpPageView.initUIComponents();
        signUpPageView.addObserver(this);
        signUpStateObservers.add(signUpPageView);
    }

    @Override
    public Component show() {
        return signUpPageView.show();
    }

    @Override
    public void addObserver(IAccountObserver observer) {
        accountObservers.add(observer);
    }

    @Override
    public void doRegister(String username, String pseudo, String password) {

        if (username.isEmpty() || pseudo.isEmpty() || password.isEmpty()) {
            signUpStateObservers.forEach(ISignUpStateObserver::fieldNotSpecified);
            return;
        }

        for (User user : database.getUsers()) {
            if (user.getUserTag().equalsIgnoreCase(pseudo)) {
                signUpStateObservers.forEach(iSignUpStateObserver -> iSignUpStateObserver.usertagAlreadyExist(pseudo));
                return;
            }
        }

        User user = new User(UUID.randomUUID(), pseudo, password, username, new HashSet<>(), ResourceManager.getResource("images/userProfil.png").getPath());
        entityManager.sendUser(user);
        signUpStateObservers.forEach(iSignUpStateObserver -> iSignUpStateObserver.registerSuccess(user));
    }

    @Override
    public void cancelRegister() {
        accountObservers.forEach(IAccountObserver::notifyUserDisconnection);
    }
}

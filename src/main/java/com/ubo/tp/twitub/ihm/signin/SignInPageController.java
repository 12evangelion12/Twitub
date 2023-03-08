package com.ubo.tp.twitub.ihm.signin;

import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.observer.IAccountObserver;
import com.ubo.tp.twitub.observer.ISignInObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SignInPageController implements IPage.IController, ISignInObserver {

    private final SignInPageView signInPageView;
    private final IDatabase database;
    private final EntityManager entityManager;
    private final List<IAccountObserver> accountObservers;
    private User user;

    public SignInPageController(IDatabase database, EntityManager entityManager) {
        this.entityManager = entityManager;
        signInPageView = new SignInPageView();
        accountObservers = new ArrayList<>();
        this.database = database;
    }

    @Override
    public void init() {
        signInPageView.addSignInObserver(this);
        signInPageView.initUIComponents();
    }

    @Override
    public Component show() {
        return signInPageView.show();
    }

    @Override
    public void login(String identifiant, String password) {

        database.getUsers().forEach(user -> {
            if (user.getName().equalsIgnoreCase(identifiant) && user.getUserPassword().equals(password)) {
                this.user = user;
            }
        });
        accountObservers.forEach(ob -> ob.notifyUserConnection(user));
    }

    @Override
    public void doRegister() {
        accountObservers.forEach(IAccountObserver::notifyUserInscription);
    }

    @Override
    public void addObserver(IAccountObserver observer) {
        this.accountObservers.add(observer);
    }
}

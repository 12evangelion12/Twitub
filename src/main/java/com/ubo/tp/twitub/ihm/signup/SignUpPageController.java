package com.ubo.tp.twitub.ihm.signup;

import com.ubo.tp.twitub.common.ResourceManager;
import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.newObserver.IAccountObserver;
import com.ubo.tp.twitub.newObserver.ISignUpControllerObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SignUpPageController implements IPage.IController, IDatabaseObserver, ISignUpControllerObserver {

    private final IDatabase database;
    private final EntityManager entityManager;
    private final SignUpPageView signUpPageView;
    private final List<IAccountObserver> accountObservers;
    private final List<ISignUpControllerObserver> signUpStateObservers;
    private User userAdded;

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
        signUpPageView.addControllerObserver(this);
        database.addObserver(this);
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
    public void register(String usertag, String username, String password) {
        if (username.isEmpty() || usertag.isEmpty() || password.isEmpty()) {
            signUpStateObservers.forEach(ISignUpControllerObserver::credentialNotSpecified);
            return;
        }

        for (User user : database.getUsers()) {
            if (user.getUserTag().equalsIgnoreCase(usertag)) {
                signUpStateObservers.forEach(ISignUpControllerObserver::usertagAlreadyUse);
                return;
            }

            if (user.getName().equalsIgnoreCase(username)) {
                signUpStateObservers.forEach(ISignUpControllerObserver::usernameAlreadyExist);
                return;
            }
        }

        User user = new User(UUID.randomUUID(), usertag, password, username, new HashSet<>(), ResourceManager.getResource("/images/userProfil.png").getPath());
        entityManager.sendUser(user);
        userAdded = user;
    }

    @Override
    public void notifyUserAdded(User addedUser) {
        //Lorsqu'un utilisateur est ajouté on vérifie que c'est notre instance qui a ajouter l'utilisateur pour afficher la popup
        if (addedUser.equals(userAdded)) {
            signUpStateObservers.forEach(ISignUpControllerObserver::registerSuccess);
        }
    }


    @Override
    public void cancelRegister() {
        accountObservers.forEach(IAccountObserver::notifyUserDisconnection);
    }
}

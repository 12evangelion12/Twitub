package main.java.com.ubo.tp.twitub.ihm.signup;

import main.java.com.ubo.tp.twitub.common.ResourceManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.observers.IAccountObserver;
import main.java.com.ubo.tp.twitub.observers.ISignUpObserver;
import main.java.com.ubo.tp.twitub.observers.ISignUpStateObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SignUpPageController implements IPage.IController, ISignUpObserver {

    private final IDatabase database;
    private final SignUpPageView signUpPageView;
    private final List<IAccountObserver> accountObservers;
    private final List<ISignUpStateObserver> signUpStateObservers;

    public SignUpPageController(IDatabase database) {
        this.database = database;
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

        User user = new User(UUID.randomUUID(), pseudo, password, username, new HashSet<>(), ResourceManager.getResource("images/userProfil.png").getPath());
        database.addUser(user);
    }

    @Override
    public void cancelRegister() {
        accountObservers.forEach(IAccountObserver::notifyUserDisconnection);
    }
}

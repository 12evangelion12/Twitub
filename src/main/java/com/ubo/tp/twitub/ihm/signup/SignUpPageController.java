package main.java.com.ubo.tp.twitub.ihm.signup;

import main.java.com.ubo.tp.twitub.core.EntityManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.newObserver.ISignUpControllerObserver;
import main.java.com.ubo.tp.twitub.observer.IAccountObserver;

import java.awt.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
        }

        URL res = getClass().getClassLoader().getResource("images/userIcon.png");
        String absolutePath = null;
        try {
            absolutePath = Paths.get(res.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            System.out.println("PAS BON");
            e.printStackTrace();
        }
        System.out.println(absolutePath);

        User user = new User(UUID.randomUUID(), usertag, password, username, new HashSet<>(), "src/main/resources/images/userProfil.png");
        entityManager.sendUser(user);
    }

    @Override
    public void notifyUserAdded(User addedUser) {
        signUpStateObservers.forEach(ISignUpControllerObserver::registerSuccess);
    }

    @Override
    public void cancelRegister() {
        accountObservers.forEach(IAccountObserver::notifyUserDisconnection);
    }
}

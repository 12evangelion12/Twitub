package main.java.com.ubo.tp.twitub.ihm;

import main.java.com.ubo.tp.twitub.common.SharedDirectoryManager;
import main.java.com.ubo.tp.twitub.component.JNavBarComponent;
import main.java.com.ubo.tp.twitub.component.JSignoutConponent;
import main.java.com.ubo.tp.twitub.core.EntityManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.profil.ProfilPageController;
import main.java.com.ubo.tp.twitub.ihm.signin.SignInPageController;
import main.java.com.ubo.tp.twitub.ihm.signup.SignUpPageController;
import main.java.com.ubo.tp.twitub.ihm.twit.TwitPageController;
import main.java.com.ubo.tp.twitub.ihm.user.UsersPageController;
import main.java.com.ubo.tp.twitub.observer.IAccountObserver;
import main.java.com.ubo.tp.twitub.observer.IMenuBarObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class StructurePageController implements IAccountObserver, IMenuBarObserver {

    protected StructurePageView structurePageView;
    protected IDatabase mDatabase;
    protected EntityManager mEntityManager;
    private User connectedUser;

    public StructurePageController(IDatabase database, EntityManager entityManager) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.structurePageView = new StructurePageView();
        structurePageView.addObserver(this);
    }

    public void showGUI() {
        structurePageView.show();
        notifyUserDisconnection();
    }

    @Override
    public void notifyUserConnection(User user) {

        if (user == null) {
            JOptionPane.showMessageDialog(structurePageView.mFrame, "Les identifiants spécifiés ne sont pas valides !", "Identifiants invalides", JOptionPane.WARNING_MESSAGE);
            return;
        }

        connectedUser = user;
        TwitPageController twitPageController = new TwitPageController(user, mDatabase, mEntityManager);
        twitPageController.init();
        twitPageController.addObserver(this);
        changeView(twitPageController.show(), true);
    }

    @Override
    public void notifyUserDisconnection() {

        connectedUser = null;
        SignInPageController signInPageController = new SignInPageController(mDatabase, mEntityManager);
        signInPageController.init();
        signInPageController.addObserver(this);
        changeView(signInPageController.show(), false);
    }

    @Override
    public void notifyUserInscription() {

        SignUpPageController signUpPageController = new SignUpPageController(mDatabase, mEntityManager);
        signUpPageController.init();
        signUpPageController.addObserver(this);
        changeView(signUpPageController.show(), false);
    }

    @Override
    public void notifyShowTwitPage(User user) {
        notifyUserConnection(user);
    }

    @Override
    public void notifyShowFollowersPage(User user) {

        UsersPageController usersPageController = new UsersPageController(user, mDatabase, mEntityManager);
        usersPageController.init();
        usersPageController.addObserver(this);
        changeView(usersPageController.show(), true);
    }

    @Override
    public void notifyShowProfilPage(User user) {

        ProfilPageController profilPageController = new ProfilPageController(user, mDatabase, mEntityManager);
        profilPageController.init();
        profilPageController.addObserver(this);
        changeView(profilPageController.show(), true);
    }

    private void initNavBarListener(JNavBarComponent navBarComponent) {

        navBarComponent.addProfilButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowProfilPage(connectedUser);
            }
        });

        navBarComponent.addTwitButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowTwitPage(connectedUser);
            }
        });

        navBarComponent.addUserButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowFollowersPage(connectedUser);
            }
        });
    }

    private void changeView(Component jPanel, boolean navBarEnable) {

        structurePageView.mFrame.getContentPane().removeAll();

        if (navBarEnable) {
            JNavBarComponent navBarComponent = new JNavBarComponent();
            initNavBarListener(navBarComponent);
            structurePageView.mFrame.getContentPane().add(navBarComponent.getComponent(), BorderLayout.WEST);

            JSignoutConponent jSignoutConponent = new JSignoutConponent();
            jSignoutConponent.initGUI();
            jSignoutConponent.addObserver(this);
            structurePageView.mFrame.getContentPane().add(jSignoutConponent.getComponent(), BorderLayout.NORTH);
        }

        structurePageView.mFrame.getContentPane().add(jPanel, BorderLayout.CENTER);
        structurePageView.mFrame.validate();
        structurePageView.mFrame.repaint();
    }

    @Override
    public void notifySharedFolderClicked() {
        new SharedDirectoryManager(mDatabase, mEntityManager).openFileChooser();
    }

    @Override
    public void notifyAboutButtonClicked() {
        JOptionPane.showMessageDialog(structurePageView.mFrame, "<html><center>UBO M2-TIIL<br>Département Informatique", "A propos", JOptionPane.PLAIN_MESSAGE, new ImageIcon("src/main/resources/images/logo_50.jpg"));
    }

    @Override
    public void notifyCloseButtonClicked() {
        System.exit(0);
    }
}

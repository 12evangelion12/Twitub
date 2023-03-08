package com.ubo.tp.twitub.ihm;

import com.ubo.tp.twitub.common.SharedDirectoryManager;
import com.ubo.tp.twitub.component.JNavBarComponent;
import com.ubo.tp.twitub.component.JSignoutConponent;
import com.ubo.tp.twitub.core.EntityManager;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.datamodel.IDatabaseObserver;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.profil.ProfilPageController;
import com.ubo.tp.twitub.ihm.signin.SignInPageController;
import com.ubo.tp.twitub.ihm.signup.SignUpPageController;
import com.ubo.tp.twitub.ihm.twit.TwitPageController;
import com.ubo.tp.twitub.ihm.user.UsersPageController;
import com.ubo.tp.twitub.observer.IAccountObserver;
import com.ubo.tp.twitub.observer.IMenuBarObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class StructurePageController implements IAccountObserver, IMenuBarObserver, IDatabaseObserver {

    protected StructurePageView structurePageView;
    protected IDatabase mDatabase;
    protected EntityManager mEntityManager;
    private User connectedUser;

    public StructurePageController(IDatabase database, EntityManager entityManager) {
        this.mDatabase = database;
        this.mDatabase.addObserver(this);
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
    public void notifyShowTwitPage() {
        notifyUserConnection(connectedUser);
    }

    @Override
    public void notifyShowFollowersPage() {

        UsersPageController usersPageController = new UsersPageController(connectedUser, mDatabase, mEntityManager);
        usersPageController.init();
        usersPageController.addObserver(this);
        changeView(usersPageController.show(), true);
    }

    @Override
    public void notifyShowProfilPage() {

        ProfilPageController profilPageController = new ProfilPageController(connectedUser, mDatabase, mEntityManager);
        profilPageController.init();
        profilPageController.addObserver(this);
        changeView(profilPageController.show(), true);
    }

    private void initNavBarListener(JNavBarComponent navBarComponent) {

        navBarComponent.addProfilButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowProfilPage();
            }
        });

        navBarComponent.addTwitButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowTwitPage();
            }
        });

        navBarComponent.addUserButtonClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                notifyShowFollowersPage();
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

    @Override
    public void notifyUserModified(User modifiedUser) {
        if (modifiedUser.getUserTag().equalsIgnoreCase(this.connectedUser.getUserTag())) {
            this.connectedUser = modifiedUser;
        }
    }
}

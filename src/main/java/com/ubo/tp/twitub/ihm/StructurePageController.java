package main.java.com.ubo.tp.twitub.ihm;

import main.java.com.ubo.tp.twitub.common.SharedDirectoryManager;
import main.java.com.ubo.tp.twitub.components.NavBarComponent;
import main.java.com.ubo.tp.twitub.core.EntityManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.signin.SignInPageController;
import main.java.com.ubo.tp.twitub.ihm.signup.SignUpPageController;
import main.java.com.ubo.tp.twitub.ihm.twit.TwitPageController;
import main.java.com.ubo.tp.twitub.observers.IAccountObserver;
import main.java.com.ubo.tp.twitub.observers.IMenuBarObserver;

import javax.swing.*;
import java.awt.*;


public class StructurePageController implements IAccountObserver, IMenuBarObserver {

    protected StructurePageView structurePageView;
    protected IDatabase mDatabase;
    protected EntityManager mEntityManager;

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

        TwitPageController twitPageController = new TwitPageController(mDatabase, user);
        twitPageController.init();
        twitPageController.addObserver(this);
        changeView(twitPageController.show(), true);
    }

    @Override
    public void notifyUserDisconnection() {

        SignInPageController signInPageController = new SignInPageController(mDatabase);
        signInPageController.init();
        signInPageController.addObserver(this);
        changeView(signInPageController.show(), false);
    }

    @Override
    public void notifyUserInscription() {

        SignUpPageController signUpPageController = new SignUpPageController(mDatabase);
        signUpPageController.init();
        signUpPageController.addObserver(this);
        changeView(signUpPageController.show(), false);
    }

    private void changeView(Component jPanel, boolean navBarEnable) {

        structurePageView.mFrame.getContentPane().removeAll();

        if (navBarEnable) {
            structurePageView.mFrame.getContentPane().add(new NavBarComponent().getComponent(), BorderLayout.WEST);
        }

        structurePageView.mFrame.getContentPane().add(jPanel, BorderLayout.CENTER);
        structurePageView.mFrame.validate();
        structurePageView.mFrame.repaint();
    }

    @Override
    public void notifySharedFolderClicked() {
        new SharedDirectoryManager(mDatabase).openFileChooser();
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

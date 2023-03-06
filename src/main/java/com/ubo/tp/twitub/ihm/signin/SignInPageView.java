package main.java.com.ubo.tp.twitub.ihm.signin;

import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.observer.ISignInObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignInPageView implements IPage.IView {

    private JPanel jPanel;
    private JButton signInButton;
    private JButton signUpButton;
    private final List<ISignInObserver> signInObservers;
    private JTextField userField;
    private JPasswordField passwordField;

    public SignInPageView() {
        signInObservers = new ArrayList<>();
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setBorder(new EmptyBorder(20, 150, 70, 150));

        JLabel applicationIcon = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/applicationIcon.png")));
            Image scaleImage = icon.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT);
            applicationIcon.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        applicationIcon.setSize(new Dimension(16, 16));
        GridBagConstraints applicationIconContraint = new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        userField = new JTextField();
        GridBagConstraints userFieldContraint = new GridBagConstraints(0, 2, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel userLabel = new JLabel("utilisateur");
        GridBagConstraints userLabelContraint = new GridBagConstraints(0, 1, 3, 1, 1, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(15, 0, 0, 0), 0, 0);

        passwordField = new JPasswordField();
        GridBagConstraints passwordFieldContraint = new GridBagConstraints(0, 4, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel passwordLabel = new JLabel("mot de passe");
        GridBagConstraints passwordLabelContraint = new GridBagConstraints(0, 3, 3, 1, 1, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(15, 0, 0, 0), 0, 0);

        signInButton = new JButton("Se connecter");
        GridBagConstraints signInButtonContraint = new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(20, 40, 20, 40), 0, 0);
        initConnectionButtonEvent();

        signUpButton = new JButton("S'inscrire");
        GridBagConstraints signUpButtonConstraint = new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(20, 40, 20, 40), 0, 0);
        initRegisterButtonEvent();

        jPanel.add(applicationIcon, applicationIconContraint);
        jPanel.add(userLabel, userLabelContraint);
        jPanel.add(userField, userFieldContraint);
        jPanel.add(passwordLabel, passwordLabelContraint);
        jPanel.add(passwordField, passwordFieldContraint);
        jPanel.add(signInButton, signInButtonContraint);
        jPanel.add(signUpButton, signUpButtonConstraint);
    }

    @Override
    public Component show() {
        return jPanel;
    }

    private void initConnectionButtonEvent() {
        signInButton.addActionListener(action -> signInObservers.forEach(ob -> ob.doLogin(userField.getText(), String.valueOf(passwordField.getPassword()))));
    }

    private void initRegisterButtonEvent() {
        signUpButton.addActionListener(action -> signInObservers.forEach(ISignInObserver::doRegister));
    }

    public void addSignInObserver(ISignInObserver observer) {
        signInObservers.add(observer);
    }
}

package main.java.com.ubo.tp.twitub.ihm.signup;

import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.observers.ISignUpObserver;
import main.java.com.ubo.tp.twitub.observers.ISignUpStateObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SignUpPageView implements IPage.IView, ISignUpStateObserver {

    private final List<ISignUpObserver> signUpObserverList;
    private JButton signUpButton;
    private JButton connectionButton;
    private JTextField identifiant;
    private JTextField pseudonyme;
    private JPasswordField password;
    private JPanel jPanel;

    public SignUpPageView() {
        signUpObserverList = new ArrayList<>();
    }

    @Override
    public Component show() {
        return jPanel;
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setBorder(new EmptyBorder(20, 150, 70, 150));

        JLabel titlePage = new JLabel("Création d'un compte twitub");
        titlePage.setFont(new Font("Serif", Font.PLAIN, 35));
        titlePage.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints titlePageContraint = new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel pseudonymeLabel = new JLabel("pseudonyme : ");
        GridBagConstraints pseudonymeLabelConstraint = new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        pseudonyme = new JTextField();
        GridBagConstraints pseudonymeConstraint = new GridBagConstraints(0, 2, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel identifiantLabel = new JLabel("identifiant : ");
        GridBagConstraints identifiantLabelConstraint = new GridBagConstraints(0, 3, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        identifiant = new JTextField();
        GridBagConstraints identifiantConstraint = new GridBagConstraints(0, 4, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel passwordLabel = new JLabel("mot de passe : ");
        GridBagConstraints passwordLabelContraint = new GridBagConstraints(0, 5, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        password = new JPasswordField();
        GridBagConstraints passwordConstraint = new GridBagConstraints(0, 6, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        signUpButton = new JButton("Créer un compte");
        GridBagConstraints signUpButtonConstraint = new GridBagConstraints(0, 7, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 150, 20, 150), 0, 0);
        initSignUpButtonListener();

        JLabel connectionLabel = new JLabel("Déjà un compte ?");
        connectionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints connectionLabelConstraint = new GridBagConstraints(0, 8, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0);

        connectionButton = new JButton("connectez-vous !");
        GridBagConstraints connectionButtonConstraint = new GridBagConstraints(1, 8, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        initCancelButtonListener();

        jPanel.add(titlePage, titlePageContraint);
        jPanel.add(pseudonyme, pseudonymeConstraint);
        jPanel.add(passwordLabel, passwordLabelContraint);
        jPanel.add(identifiantLabel, identifiantLabelConstraint);
        jPanel.add(identifiant, identifiantConstraint);
        jPanel.add(pseudonymeLabel, pseudonymeLabelConstraint);
        jPanel.add(password, passwordConstraint);
        jPanel.add(signUpButton, signUpButtonConstraint);
        jPanel.add(connectionLabel, connectionLabelConstraint);
        jPanel.add(connectionButton, connectionButtonConstraint);
    }

    public void initSignUpButtonListener() {
        signUpButton.addActionListener(action -> signUpObserverList.forEach(observer -> observer.doRegister(identifiant.getText(), pseudonyme.getText(), String.valueOf(password.getPassword()))));
    }

    public void initCancelButtonListener() {
        connectionButton.addActionListener(action -> signUpObserverList.forEach(ISignUpObserver::cancelRegister));
    }

    public void addObserver(ISignUpObserver observer) {
        signUpObserverList.add(observer);
    }

    @Override
    public void fieldNotSpecified() {
        JOptionPane.showMessageDialog(jPanel, "Merci de spécifier tous les champs disponibles !", "Champs manquants", JOptionPane.WARNING_MESSAGE);
    }
}

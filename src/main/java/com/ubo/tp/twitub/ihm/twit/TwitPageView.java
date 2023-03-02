package main.java.com.ubo.tp.twitub.ihm.twit;

import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.observers.ISignOutObserver;
import main.java.com.ubo.tp.twitub.observers.ITwitSendObserver;
import main.java.com.ubo.tp.twitub.observers.ITwitStateObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TwitPageView implements IPage.IView, ITwitStateObserver {

    private JPanel jPanel;
    private final List<ISignOutObserver> logoutObservers;
    private final List<ITwitSendObserver> twitSendObservers;
    private JButton disconnectionButton;
    private JButton twitSendButton;
    private JTextField twitTextField;
    private JLabel twitStateLabel;
    private final String username;

    public TwitPageView(String username) {
        this.username = username;
        logoutObservers = new ArrayList<>();
        twitSendObservers = new ArrayList<>();
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        disconnectionButton = new JButton("Deconnexion");
        GridBagConstraints disconnectionButtonConstraints = new GridBagConstraints( 0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,10,10,0), 0, 0);
        initDisconnectionButtonEvent();

        twitSendButton = new JButton("Envoyer le twit");
        GridBagConstraints twitSendConstraints = new GridBagConstraints( 2, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,0,10,10), 0, 0);
        initTwitButtonEvent();

        twitTextField = new JTextField();
        GridBagConstraints twitTextFieldConstraints = new GridBagConstraints( 0, 1, 3, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0,10,0,0), 0, 0);

        JLabel twitSenderLabel = new JLabel();
        GridBagConstraints twitSenderLabelConstraints = new GridBagConstraints( 0, 0, 3, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0,10,10,0), 0, 0);
        twitSenderLabel.setText("Utilisateur : "+username);

        twitStateLabel = new JLabel();
        GridBagConstraints twitStateLabelConstraints = new GridBagConstraints( 0, 2, 3, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,10,10,0), 0, 0);


        jPanel.add(disconnectionButton, disconnectionButtonConstraints);
        jPanel.add(twitSendButton, twitSendConstraints);
        jPanel.add(twitTextField, twitTextFieldConstraints);
        jPanel.add(twitSenderLabel, twitSenderLabelConstraints);
        jPanel.add(twitStateLabel, twitStateLabelConstraints);
    }

    @Override
    public JPanel show() {
        return jPanel;
    }

    private void initDisconnectionButtonEvent() {
        disconnectionButton.addActionListener(action -> logoutObservers.forEach(ISignOutObserver::doLogout));
    }

    public void addLogoutObserver(ISignOutObserver observer) {
        logoutObservers.add(observer);
    }

    public void addTwitObserver(ITwitSendObserver observer) {
        twitSendObservers.add(observer);
    }

    private void initTwitButtonEvent() {
        twitSendButton.addActionListener(action -> twitSendObservers.forEach(observer -> observer.sendTwit(twitTextField.getText())));
    }

    @Override
    public void twitTooLong() {
        twitStateLabel.setText("Le twit est trop long ! ("+twitTextField.getText().length()+">250)  <(｀^´)>");
    }

    @Override
    public void twitAccepted() {
        twitStateLabel.setText("Le twit à été envoyé ! :)");
        twitTextField.setText("");
    }
}

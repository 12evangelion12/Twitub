package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.observer.IAccountObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JSignoutComponent implements JComponent {

    private final List<IAccountObserver> accountObserverList;
    private JPanel jPanel;
    private JButton signOutButton;

    public JSignoutComponent() {
        accountObserverList = new ArrayList<>();
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        signOutButton = new JButton("Se déconnecter");
        GridBagConstraints signOutButtonContraint = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 20), 0, 0);
        initSignOutListener();

        jPanel.add(signOutButton, signOutButtonContraint);
    }

    private void initSignOutListener() {
        signOutButton.addActionListener(e -> accountObserverList.forEach(IAccountObserver::notifyUserDisconnection));
    }

    public void addObserver(IAccountObserver observer) {
        accountObserverList.add(observer);
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }
}

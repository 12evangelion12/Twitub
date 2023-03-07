package com.ubo.tp.twitub.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;

public class JNavBarComponent implements JComponent {

    JPanel jPanel;
    JButton users;
    JButton profil;
    JButton twit;

    public JNavBarComponent() {
        jPanel = new JPanel();
        initGUI();
    }

    @Override
    public void initGUI() {

        jPanel.setLayout(new GridBagLayout());
        jPanel.setBorder(new EmptyBorder(20, 10, 20, 20));

        users = new JButton();
        try {
            ImageIcon usersIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuUsers.png")));
            Image scaleImage = usersIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            users.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints usersConstraint = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);
        users.setToolTipText("Liste des utilisateurs");

        profil = new JButton();
        try {
            ImageIcon settingsIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuProfil.png")));
            Image scaleImage = settingsIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            profil.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints settingsConstraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);
        profil.setToolTipText("Afficher le profil");

        twit = new JButton();
        try {
            ImageIcon twitIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuTwit.png")));
            Image scaleImage = twitIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            twit.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints twitConstraint = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);
        twit.setToolTipText("Twitter et afficher la liste");

        jPanel.add(profil, settingsConstraint);
        jPanel.add(twit, twitConstraint);
        jPanel.add(users, usersConstraint);

    }

    public void addUserButtonClickedListener(MouseAdapter mouseAdapter) {
        users.addMouseListener(mouseAdapter);
    }

    public void addTwitButtonClickedListener(MouseAdapter mouseAdapter) {
        twit.addMouseListener(mouseAdapter);
    }

    public void addProfilButtonClickedListener(MouseAdapter mouseAdapter) {
        profil.addMouseListener(mouseAdapter);
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }
}

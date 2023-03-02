package main.java.com.ubo.tp.twitub.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NavBarComponent implements Component {

    JPanel jPanel;

    public NavBarComponent() {
        jPanel = new JPanel();
        initGUI();
    }

    @Override
    public void initGUI() {

        jPanel.setLayout(new GridBagLayout());
        jPanel.setBorder(new EmptyBorder(20, 10, 20, 20));

        JButton users = new JButton();
        try {
            ImageIcon usersIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuUsers.png")));
            Image scaleImage = usersIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            users.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints usersConstraint = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);


        JButton settings = new JButton();
        try {
            ImageIcon settingsIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuProfil.png")));
            Image scaleImage = settingsIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            settings.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints settingsConstraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);


        JButton twit = new JButton();
        try {
            ImageIcon twitIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menuTwit.png")));
            Image scaleImage = twitIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            twit.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints twitConstraint = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 0, 10, 0), 0, 0);

        jPanel.add(settings, settingsConstraint);
        jPanel.add(twit, twitConstraint);
        jPanel.add(users, usersConstraint);

    }

    @Override
    public java.awt.Component getComponent() {
        return jPanel;
    }
}

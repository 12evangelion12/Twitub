package main.java.com.ubo.tp.twitub.components;

import main.java.com.ubo.tp.twitub.datamodel.Twit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TwitComponent implements Component {

    JPanel jPanel;
    Twit twit;

    public TwitComponent(Twit twit) {
        jPanel = new JPanel();
        this.twit = twit;
    }

    @Override
    public void initGUI() {

        jPanel.setLayout(new GridBagLayout());

        JLabel userProfilImage = new JLabel();
        try {
            userProfilImage.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/images/userProfil.jpg"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public java.awt.Component getComponent() {

        return null;
    }
}

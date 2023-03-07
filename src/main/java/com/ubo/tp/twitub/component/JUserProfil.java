package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.model.UserProfil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class JUserProfil implements JComponent {

    private JPanel jPanel;
    private final UserProfil userProfil;
    private JLabel twitCount;

    public JUserProfil(UserProfil userProfil) {
        this.userProfil = userProfil;
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        JLabel userProfil = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(this.userProfil.getUser().getAvatarPath())));
            Image scaleImage = icon.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT);
            userProfil.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints userProfilContraint = new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        JLabel userTag = new JLabel("Pseudonyme : @" + this.userProfil.getUser().getUserTag());
        userTag.setFont(new Font("Serif", Font.PLAIN, 20));
        userTag.setHorizontalAlignment(SwingConstants.LEFT);

        GridBagConstraints userTagContraint = new GridBagConstraints(1, 0, 2, 1, 2, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        JLabel userName = new JLabel("Nom d'utilisateur : " + this.userProfil.getUser().getName());
        userName.setFont(new Font("Serif", Font.PLAIN, 20));
        userName.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints userNameContraint = new GridBagConstraints(1, 1, 2, 1, 2, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        twitCount = new JLabel();
        setCount(this.userProfil.getTwitCount());
        twitCount.setFont(new Font("Serif", Font.PLAIN, 30));
        twitCount.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints twitCountContraint = new GridBagConstraints(3, 0, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        jPanel.add(userProfil, userProfilContraint);
        jPanel.add(userTag, userTagContraint);
        jPanel.add(userName, userNameContraint);
        jPanel.add(twitCount, twitCountContraint);
    }

    public void setCount(int count) {
        twitCount.setText(count + " Twits");
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }
}

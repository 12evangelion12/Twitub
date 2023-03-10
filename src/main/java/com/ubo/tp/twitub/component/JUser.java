package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.TwitubLauncher;
import com.ubo.tp.twitub.common.Constants;
import com.ubo.tp.twitub.datamodel.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class JUser implements JComponent {

    private final User user;
    private final boolean followSelected;
    private JPanel jPanel;
    private JToggleButton followed;

    public JUser(User user, boolean followSelected) {
        this.user = user;
        this.followSelected = followSelected;
    }

    @Override
    public void initGUI() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        JLabel userProfil = new JLabel();
        ImageIcon icon = null;
        try {
            File file = new File(user.getAvatarPath());
            icon = new ImageIcon(ImageIO.read(file));
        } catch (Exception e) {
            TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Impossible de charger l'icone de l'utilisateur, chargement de l'icone par défaut en cours...");
            try {
                icon = new ImageIcon(ImageIO.read(new File(Constants.DEFAULT_USER_ICON_PATH)));
                TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Chargement de l'icone par défaut réussi !");
            } catch (IOException ex) {
                TwitubLauncher.getLogger(getClass()).log(Level.INFO, "Impossible de charger l'icone par défaut");
            }
        }
        if (icon != null) {
            Image scaleImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
            userProfil.setIcon(new ImageIcon(scaleImage));
        }
        GridBagConstraints userProfilContraint = new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        JLabel userTag = new JLabel("userTag: @" + user.getUserTag());
        GridBagConstraints userTagContraint = new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JLabel userName = new JLabel("utilisateur: " + user.getName());
        GridBagConstraints userNameContraint = new GridBagConstraints(1, 1, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        followed = new JToggleButton();
        followed.setSelected(followSelected);
        followButtonChange();
        followed.addChangeListener(e -> followButtonChange());
        GridBagConstraints followedContraint = new GridBagConstraints(3, 0, 1, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 20, 10, 20), 0, 0);

        jPanel.add(userProfil, userProfilContraint);
        jPanel.add(userTag, userTagContraint);
        jPanel.add(userName, userNameContraint);
        jPanel.add(followed, followedContraint);

        jPanel.setBorder(new LineBorder(Color.gray));
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    public boolean followButtonState() {
        TwitubLauncher.getLogger(getClass()).log(Level.INFO, "{}", followed.isSelected());
        return followed.isSelected();
    }

    public void addMouseListener(MouseAdapter mouseAdapter) {
        followed.addMouseListener(mouseAdapter);
    }

    private void followButtonChange() {
        followed.setText(followed.isSelected() ? "Unfollow" : "Follow");
        followed.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.red;
            }
        });
    }
}

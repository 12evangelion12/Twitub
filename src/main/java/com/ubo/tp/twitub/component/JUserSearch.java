package com.ubo.tp.twitub.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;

public class JUserSearch implements JComponent {
    private JPanel jPanel;
    private JTextField searchText;
    private JButton searchButton;

    public JUserSearch() {
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        searchText = new JTextField();
        GridBagConstraints searchTextContraint = new GridBagConstraints(0, 0, 1, 1, 20, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        searchButton = new JButton();
        try {
            ImageIcon settingsIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/searchIcon.png")));
            Image scaleImage = settingsIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            searchButton.setIcon(new ImageIcon(scaleImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GridBagConstraints searchButtonContraint = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        jPanel.add(searchText, searchTextContraint);
        jPanel.add(searchButton, searchButtonContraint);
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    public void initSearchButtonListener(MouseAdapter mouseAdapter) {
        searchButton.addMouseListener(mouseAdapter);
    }

    public String getSearchingText() {
        return searchText.getText();
    }
}

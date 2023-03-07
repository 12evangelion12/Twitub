package main.java.com.ubo.tp.twitub.component;

import main.java.com.ubo.tp.twitub.observer.ISearchItemObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JUserSearch implements JComponent {

    private JPanel jPanel;
    private JTextField searchText;
    private JButton searchButton;
    private final List<ISearchItemObserver> searchItems;

    public JUserSearch() {
        searchItems = new ArrayList<>();
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
        initSearchButtonListener();

        jPanel.add(searchText, searchTextContraint);
        jPanel.add(searchButton, searchButtonContraint);
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    private void initSearchButtonListener() {
        searchButton.addActionListener(e -> searchItems.forEach(ISearchItemObserver::notifySearchButtonClicked));
    }

    public void addObserver(ISearchItemObserver observer) {
        searchItems.add(observer);
    }

    public String getSearchingText() {
        return searchText.getText();
    }
}

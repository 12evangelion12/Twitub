package com.ubo.tp.twitub.ihm;

import com.ubo.tp.twitub.TwitubLauncher;
import com.ubo.tp.twitub.observer.IMenuBarObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class StructurePageView implements IPage.IView {

    private final Dimension screenSize;
    private final List<IMenuBarObserver> menuBarObserverList;
    protected JFrame mFrame;
    private JMenuItem closeMenu;
    private JMenuItem partagedFolderMenu;
    private JButton aboutMenu;

    public StructurePageView() {
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        menuBarObserverList = new ArrayList<>();
    }

    @Override
    public JPanel show() {
        // Init auto de l'IHM au cas ou ;)
        if (mFrame == null) {
            this.initUIComponents();
        }

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(() -> {
            // Custom de l'affichage
            JFrame frame = StructurePageView.this.mFrame;
            frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                    (screenSize.height - frame.getHeight()) / 4);

            // Affichage
            StructurePageView.this.mFrame.setVisible(true);
        });

        return null;
    }

    @Override
    public void initUIComponents() {
        // Création de la fenetre principale
        mFrame = new JFrame("twItUb");
        mFrame.setLayout(new BorderLayout());
        mFrame.setSize((screenSize.width - mFrame.getWidth()) / 2,
                (screenSize.height - mFrame.getHeight()) / 2);


        //Définition de l'icone de l'application
        try {
            mFrame.setIconImage(ImageIO.read(new File("src/main/resources/images/applicationIcon.png")));
        } catch (IOException e) {
            TwitubLauncher.getLogger(getClass()).log(Level.SEVERE, "Impossible de récupérer l'icone de l'application", e);
        }

        //Ajout d'un menuBar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu files = new JMenu("Fichier");
        JMenu help = new JMenu("?");

        //Création d'un sous menu close pour le menu "Fichier"
        try {
            closeMenu = new JMenuItem("Quitter", new ImageIcon(ImageIO.read(new File("src/main/resources/images/exitIcon_20.png"))));
            initCloseButtonListener();
            files.add(closeMenu);
        } catch (IOException e) {
            TwitubLauncher.getLogger(getClass()).log(Level.SEVERE, "Impossible de récupérer l'icone de fermeture de l'application du menu", e);
        }

        //Création d'un sous menu dossier partage pour le menu "Fichier"
        try {
            ImageIcon partagedFolderIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/partagedFolder.png")));
            Image scaleImage = partagedFolderIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            partagedFolderMenu = new JMenuItem("Dossier partagé", new ImageIcon(scaleImage));
            initFolderButtonListener();
            files.add(partagedFolderMenu);
        } catch (IOException e) {
            TwitubLauncher.getLogger(getClass()).log(Level.SEVERE, "Impossible de récupérer l'icone du dossier de partage du menu", e);
        }

        //Création du bouton permettant d'afficher le dialog "help"
        aboutMenu = new JButton("About");
        initAboutButtonListener();
        help.add(aboutMenu);

        jMenuBar.add(files);
        jMenuBar.add(help);
        mFrame.setJMenuBar(jMenuBar);
    }

    public void addObserver(IMenuBarObserver observer) {
        menuBarObserverList.add(observer);
    }

    private void initAboutButtonListener() {
        aboutMenu.addActionListener(e -> menuBarObserverList.forEach(IMenuBarObserver::notifyAboutButtonClicked));
    }

    private void initCloseButtonListener() {
        closeMenu.addActionListener(e -> menuBarObserverList.forEach(IMenuBarObserver::notifyCloseButtonClicked));
    }

    private void initFolderButtonListener() {
        partagedFolderMenu.addActionListener(e -> menuBarObserverList.forEach(IMenuBarObserver::notifySharedFolderClicked));
    }
}

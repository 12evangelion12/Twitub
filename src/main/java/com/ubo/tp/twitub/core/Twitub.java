package com.ubo.tp.twitub.core;

import com.ubo.tp.twitub.TwitubLauncher;
import com.ubo.tp.twitub.common.Constants;
import com.ubo.tp.twitub.common.PropertiesManager;
import com.ubo.tp.twitub.common.SharedDirectoryManager;
import com.ubo.tp.twitub.datamodel.Database;
import com.ubo.tp.twitub.datamodel.IDatabase;
import com.ubo.tp.twitub.ihm.StructurePageController;
import com.ubo.tp.twitub.ihm.TwitubMock;
import com.ubo.tp.twitub.observer.DataBaseObserverImpl;

import javax.swing.*;
import java.util.logging.Level;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class Twitub {
    /**
     * Base de données.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;


    /**
     * Nom de la classe de l'UI.
     */
    protected String mUiClassName;

    /**
     * Constructeur.
     */
    public Twitub() {

        // Init du look and feel de l'application
        this.initLookAndFeel();

        // Initialisation de la base de données
        this.initDatabase();

        String mockProperty = PropertiesManager.loadProperties(Constants.CONFIGURATION_FILE).getProperty(Constants.CONFIGURATION_KEY_MOCK_ENABLED);
        if (Boolean.valueOf(mockProperty)) {
            // Initialisation du bouchon de travail
            this.initMock();
        }

        // Initialisation de l'IHM
        this.initGui();

        // Initialisation du répertoire d'échange
        new SharedDirectoryManager(mDatabase, mEntityManager).initDirectory();
    }

    /**
     * Initialisation du look and feel de l'application.
     */
    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            TwitubLauncher.getLogger(getClass()).log(Level.SEVERE, "Impossible de définir le 'LookAndFeel' de l'application", e);
        }
    }

    /**
     * Initialisation de l'interface graphique.
     */
    protected void initGui() {
        StructurePageController structurePageController = new StructurePageController(this.mDatabase, this.mEntityManager);
        structurePageController.showGUI();
    }

    /**
     * Initialisation du mode bouchoné de l'application
     */
    protected void initMock() {
        TwitubMock mock = new TwitubMock(this.mDatabase, this.mEntityManager);
        mock.showGUI();
    }

    /**
     * Initialisation de la base de données
     */
    protected void initDatabase() {
        mDatabase = new Database();
        mDatabase.addObserver(new DataBaseObserverImpl());
        this.mEntityManager = new EntityManager(mDatabase);
    }

    public void show() {
        // ... setVisible?
    }
}

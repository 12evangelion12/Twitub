package main.java.com.ubo.tp.twitub.core;

import main.java.com.ubo.tp.twitub.common.Constants;
import main.java.com.ubo.tp.twitub.common.PropertiesManager;
import main.java.com.ubo.tp.twitub.common.SharedDirectoryManager;
import main.java.com.ubo.tp.twitub.datamodel.Database;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.events.file.IWatchableDirectory;
import main.java.com.ubo.tp.twitub.ihm.StructurePageController;
import main.java.com.ubo.tp.twitub.ihm.StructurePageView;
import main.java.com.ubo.tp.twitub.ihm.TwitubMock;
import main.java.com.ubo.tp.twitub.observers.DataBaseObserverImpl;

import javax.swing.*;

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
     * Vue principale de l'application.
     */
    protected StructurePageView mMainView;

    /**
     * Classe de surveillance de répertoire
     */
    protected IWatchableDirectory mWatchableDirectory;

    /**
     * Répertoire d'échange de l'application.
     */
    protected String mExchangeDirectoryPath;

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
        new SharedDirectoryManager(mDatabase).initDirectory();
    }

    /**
     * Initialisation du look and feel de l'application.
     */
    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
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
    }

    public void show() {
        // ... setVisible?
    }
}

package main.java.com.ubo.tp.twitub.common;

import main.java.com.ubo.tp.twitub.core.EntityManager;
import main.java.com.ubo.tp.twitub.datamodel.IDatabase;
import main.java.com.ubo.tp.twitub.events.file.IWatchableDirectory;
import main.java.com.ubo.tp.twitub.events.file.WatchableDirectory;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Properties;

public class SharedDirectoryManager {

    /**
     * Classe de surveillance de répertoire
     */
    protected IWatchableDirectory mWatchableDirectory;

    /**
     * Répertoire d'échange de l'application.
     */
    protected String mExchangeDirectoryPath;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    protected Properties properties;

    public SharedDirectoryManager(IDatabase database) {
        mEntityManager = new EntityManager(database);
        properties = PropertiesManager.loadProperties(Constants.CONFIGURATION_FILE);
    }

    /**
     * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
     * chooser). <br/>
     * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
     * pouvoir utiliser l'application</b>
     */
    public void initDirectory() {

        String directoryPath = properties.getProperty(Constants.CONFIGURATION_KEY_EXCHANGE_DIRECTORY);
        if (directoryPath == null || directoryPath.isEmpty()) {
            openFileChooser();
        }
    }

    public void openFileChooser() {

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setDialogTitle("Choisir le répertoire d'échange");

        switch (jFileChooser.showOpenDialog(null)) {

            case JFileChooser.APPROVE_OPTION:
                boolean isFolderValid = isValideExchangeDirectory(jFileChooser.getSelectedFile().getAbsoluteFile());
                if (isFolderValid) {
                    properties.setProperty(Constants.CONFIGURATION_KEY_EXCHANGE_DIRECTORY, String.valueOf(jFileChooser.getSelectedFile().getAbsoluteFile()));
                    PropertiesManager.writeProperties(properties, Constants.CONFIGURATION_FILE);
                    initDirectory(jFileChooser.getSelectedFile().getAbsolutePath());
                    System.out.println("\nLe dossier partagé à été défini sur le dossier : " + jFileChooser.getSelectedFile().getAbsoluteFile());
                } else {
                    JOptionPane.showMessageDialog(jFileChooser, "Le dossier selectionné n'est pas valide !", "Répertoire d'échange invalide !", JOptionPane.WARNING_MESSAGE);
                    openFileChooser();
                }
                break;

            case JFileChooser.CANCEL_OPTION:
                showErrorOptionDialog();
                openFileChooser();
                break;
        }
    }

    private void showErrorOptionDialog() {

        int result = JOptionPane.showOptionDialog(null, "La selection d'un répertoire de partage est nécéssaire pour le bon fonctionne de l'application", "Répertoire d'échange", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, Arrays.asList("Ok", "Annuler").toArray(), 0);
        //Si l'utilisateur clique sur "Annuler" on fermer l'application
        if (result == 1) {
            System.exit(0);
        }
    }

    /**
     * Indique si le fichier donné est valide pour servire de répertoire
     * d'échange
     *
     * @param directory , Répertoire à tester.
     */
    protected boolean isValideExchangeDirectory(File directory) {
        // Valide si répertoire disponible en lecture et écriture
        return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
                && directory.canWrite();
    }


    /**
     * Initialisation du répertoire d'échange.
     *
     * @param directoryPath
     */
    private void initDirectory(String directoryPath) {
        mExchangeDirectoryPath = directoryPath;
        mWatchableDirectory = new WatchableDirectory(directoryPath);
        mEntityManager.setExchangeDirectory(directoryPath);
        mWatchableDirectory.initWatching();
        mWatchableDirectory.addObserver(mEntityManager);
    }
}

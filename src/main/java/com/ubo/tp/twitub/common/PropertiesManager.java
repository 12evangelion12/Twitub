package com.ubo.tp.twitub.common;

import com.ubo.tp.twitub.TwitubLauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Classe utilitaire de gestion du chargement et de la sauvegarde des
 * configuration.
 *
 * @author S.Lucas
 */
public class PropertiesManager {
    /**
     * Chargement des propriétés de configuration de l'application (depuis un
     * éventuel fichier de configuration).
     */
    public static Properties loadProperties(String configurationFilePath) {
        Properties properties = new Properties();

        // Si le fichier de configuration existe
        if (new File(configurationFilePath).exists()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(configurationFilePath);
                properties.load(in);
            } catch (Exception e) {
                TwitubLauncher.getLogger(PropertiesManager.class).log(Level.INFO, "Impossible de charger les configurations", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        TwitubLauncher.getLogger(PropertiesManager.class).log(Level.SEVERE, "Erreur lors de la fermeture du flux sur le fichier de configuration", e);
                    }
                }
            }
        }

        return properties;
    }

    /**
     * Ecriture du fichier de configuration.
     *
     * @param properties            , Configurations enregistrées?§
     * @param configurationFilePath , Chemin du fichier de configuration à écrire.
     */
    public static void writeProperties(Properties properties, String configurationFilePath) {
        if (properties != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(configurationFilePath);
                properties.store(out, "Configuration de l'application TwitUb");
            } catch (Exception e) {
                TwitubLauncher.getLogger(PropertiesManager.class).log(Level.SEVERE, "Impossible d'enregistrer les configurations", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        TwitubLauncher.getLogger(PropertiesManager.class).log(Level.SEVERE, "Erreur lors de la fermeture du flux sur le fichier de configuration", e);
                    }
                }
            }
        }
    }
}

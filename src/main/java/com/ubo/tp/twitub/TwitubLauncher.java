package com.ubo.tp.twitub;

import com.ubo.tp.twitub.core.Twitub;

import java.util.logging.Logger;

/**
 * Classe de lancement de l'application.
 *
 * @author S.Lucas
 */
public class TwitubLauncher {

    /**
     * Launcher.
     *
     * @param args
     */
    public static void main(String[] args) {
        Twitub twitub = new Twitub();
        twitub.show();
    }

    public static Logger getLogger(Class<?> clzz) {
        return Logger.getGlobal();
    }
}

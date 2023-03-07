package main.java.com.ubo.tp.twitub.common;

import main.java.com.ubo.tp.twitub.TwitubLauncher;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceManager {

    public static InputStream getResourceAsStream(String resourceName) {
        return TwitubLauncher.class.getClassLoader().getResourceAsStream(resourceName);
    }

    public static URL getResource(String resourceName) {
        try {
            return new URL("src" + File.separator + "main" + File.separator + "resources" + File.separator + resourceName);
        } catch (MalformedURLException e) {
            System.out.println("Impossible de récupérer la resources\n" + e);
            return null;
        }
    }
}

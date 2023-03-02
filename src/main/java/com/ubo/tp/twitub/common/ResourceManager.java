package main.java.com.ubo.tp.twitub.common;

import main.java.com.ubo.tp.twitub.TwitubLauncher;

import java.io.InputStream;
import java.net.URL;

public class ResourceManager {

    public static InputStream getResourceAsStream(String resourceName) {
        return TwitubLauncher.class.getClassLoader().getResourceAsStream(resourceName);
    }

    public static URL getResource(String resourceName) {
        return TwitubLauncher.class.getClassLoader().getResource(resourceName);
    }
}

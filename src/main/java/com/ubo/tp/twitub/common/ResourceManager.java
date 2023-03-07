package com.ubo.tp.twitub.common;

import java.net.MalformedURLException;
import java.net.URL;

public class ResourceManager {

    public static URL getResource(String resourceName) {
        try {
            return new URL("src/main/resources/" + resourceName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

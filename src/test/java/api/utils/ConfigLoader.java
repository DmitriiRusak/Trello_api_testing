package api.utils;

import java.io.IOException;

public class ConfigLoader {

    public static String getProperty(String key) throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        return System.getProperty(key);
    }
}

package api.utils;

import java.io.IOException;

//This class loads credentials needed for Trello API access
//credentials (key and token) are stored in resources.config.properties file

public class ConfigLoader {

    public static String getProperty(String key) throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        return System.getProperty(key);
    }
}

package com.github.moderarti.property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.moderarti.property.PropertiesENUM.*;

public class PropertiesHandler {

    private static final Logger LOGGER = Logger.getLogger(PropertiesHandler.class.toString());

    private static final String PATH_TO_PROPERTIES = "src/main/resources/server.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final HashMap<PropertiesENUM, String> PROPERTIES_MAP;

    static {
        PROPERTIES_MAP = new HashMap<>();
        PROPERTIES_MAP.put(SERVER_PORT, "server.port");
        PROPERTIES_MAP.put(DB_DIALECT, "db.dialect");
        PROPERTIES_MAP.put(DB_DRIVER, "db.driver");
        PROPERTIES_MAP.put(DB_URL, "db.url");
        PROPERTIES_MAP.put(DB_NAME, "db.name");
        PROPERTIES_MAP.put(DB_PASSWORD, "db.pass");
        PROPERTIES_MAP.put(DB_SHOW_SQL, "db.show_sql");
        PROPERTIES_MAP.put(DB_HBM2DDL_AUTO, "db.hbm2ddl.auto");
        try {
            PROPERTIES.load(new FileInputStream(PATH_TO_PROPERTIES));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Can't find .properties file", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Some IO problem", e);
        }
    }

    private PropertiesHandler() {}

    public static String getProperty(PropertiesENUM property) {
        return PROPERTIES.getProperty(PROPERTIES_MAP.get(property));
    }

}

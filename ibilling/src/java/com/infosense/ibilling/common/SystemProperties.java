package com.infosense.ibilling.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This is a Singleton call that provides the system properties from
 * the ibilling.properties file
 */
public class SystemProperties {
    private static final Logger LOG = Logger.getLogger(SystemProperties.class);

    private static final String IBILLING_HOME = "IBILLING_HOME";
    private static final String PROPERTIES_FILE = "ibilling.properties";
    private static final String RESOURCES_DIR = "resources";
    private static final String BASE_DIR_PROPERTY = "base_dir";

    private static SystemProperties INSTANCE;

    private String resourcesDir = null;
    private Properties prop = null;

    /*
        private singleton constructor
     */
    private SystemProperties() throws IOException {
        File properties = getPropertiesFile();
        FileInputStream stream = new FileInputStream(properties);

        prop = new Properties();
        prop.load(stream);

        stream.close();

        LOG.debug("System properties loaded from: " + properties.getPath());
        System.out.println("System properties loaded from: " + properties.getPath());

        resourcesDir = getIBillingResourcesDir();

        LOG.debug("Resolved ibilling resources directory to: " + resourcesDir);
        System.out.println("Resolved ibilling resources directory to: " + resourcesDir);
    }

    /**
     * Returns a singleton instance of SystemProperties
     *
     * @return instance
     * @throws IOException if properties could not be loaded
     */
    public static SystemProperties getSystemProperties()  throws IOException{
        if (INSTANCE == null)
            INSTANCE = new SystemProperties();
        return INSTANCE;
    }

    /**
     * Returns the ibilling home path where resources and configuration files
     * can be found.
     *
     * The environment variable IBILLING_HOME and system property IBILLING_HOME are examined
     * for this value, with precedence given to system properties set via command line arguments.
     *
     * If no ibilling home path is set, properties will be loaded from the classpath.
     *
     * @return ibilling home path
     */
    public static String getIBillingHome() {
        String ibillingHome = System.getProperty(IBILLING_HOME);

        if (ibillingHome == null) {
            ibillingHome = System.getenv(IBILLING_HOME);
        }

        return ibillingHome;
    }

    /**
     * Returns the path to the ibilling resources directory.
     *
     * The resources directory is always assumed to be located in IBILLING_HOME. If IBILLING_HOME is not
     * set, this method will return a relative path as the default location for the resources directory.
     *
     * @return path to the resources directory
     */
    public String getIBillingResourcesDir() {
        // try IBILLING_HOME
        String ibillingHome = getIBillingHome();
        if (ibillingHome != null) {
            return ibillingHome + File.separator + RESOURCES_DIR + File.separator;
        }

        try {
            // try root dir
            File resources = new File("." + File.separator + RESOURCES_DIR);
            if (resources.exists()) {
                return resources.getCanonicalPath() + File.separator;
            }

            // try one level down (tomcat root)
            resources = new File(".." + File.separator + RESOURCES_DIR);
            if (resources.exists()) {
                return resources.getCanonicalPath() + File.separator;
            }
        } catch (IOException e) {
            LOG.warn("IOException when attempting to resolve canonical path to ibilling resources/", e);
        }

        return "";
    }

    /**
     * Returns the path to the ibilling.properties file.
     *
     * @return properties file
     */
    public static File getPropertiesFile() {
        String ibillingHome = getIBillingHome();
        if (ibillingHome != null) {
            // properties file from filesystem
            return new File(ibillingHome + File.separator + PROPERTIES_FILE);

        } else {
            // properties file from classpath
            URL url = SystemProperties.class.getResource("/" + PROPERTIES_FILE);
            try {
				return new File(url.toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException("Property file not found.");
			}
        }
    }

    public String get(String key) throws Exception {
        // "base_dir" should always resolve to the IBILLING_HOME resources dir
        // this value is no longer part of ibilling.properties
        if (BASE_DIR_PROPERTY.equals(key)) {
            return resourcesDir;
        }

        // get value from ibilling.properties
        String value = prop.getProperty(key);

        if (value == null)
            throw new Exception("Missing system property: " + key);

        return value;
    }
    
    public String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}

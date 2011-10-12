package geospace;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jpenton
 */
public class PropertyManager {
    private Properties properties;
    
    private PropertyManager() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream("./resources/geospace.properties"));
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static class PropertyManagerSingleton {
        public static final PropertyManager instance = new PropertyManager();
    }

    public static PropertyManager getInstance() {
        return PropertyManagerSingleton.instance;
    }
    
    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }
    
    public String getProperty(String propertyName, String defaultValue) {
        return this.properties.getProperty(propertyName, defaultValue);
    }
    
    public Integer getInteger(String propertyName) {
        String value = this.properties.getProperty(propertyName);
        return Integer.valueOf(value);
    }
    
    public Integer getInteger(String propertyName, Integer defaultValue) {
        String value = this.properties.getProperty(propertyName);
        return value == null ? defaultValue : Integer.valueOf(value);
    }
    
    public boolean getBoolean(String propertyName) {
        String value = this.properties.getProperty(propertyName);
        
        if(value == null) {
            return false;
        }
        
        return value.equalsIgnoreCase("true");
    }
}

/*
 * Copyright (c) 2011, GeoJosh - https://github.com/GeoJosh
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package geospace;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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

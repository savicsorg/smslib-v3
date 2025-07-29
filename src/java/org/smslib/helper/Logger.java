// SMSLib for Java v3
// A Java API library for sending and receiving SMS via a GSM modem
// or other supported gateways.
// Web Site: http://www.smslib.org
//
// Copyright (C) 2002-2012, Thanasis Delenikas, Athens/GREECE.
// SMSLib is distributed under the terms of the Apache License version 2.0
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.smslib.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

public class Logger {
    
    public final String FILE_SEP = System.getProperty("file.separator");
    public final String USER_DIR = System.getProperty("user.dir");
    public final String COMMON_APP_DATA_FOLDER = (System.getProperty("os.name").contains("XP")) ? "Documents and Settings" + FILE_SEP + "All Users" + FILE_SEP + "Application Data" : "ProgramData";
    public final String USER_HOME = System.getenv("SystemDrive") + FILE_SEP + COMMON_APP_DATA_FOLDER;
    
    private static Logger logger = new Logger();

    public static Logger getInstance() {
        return logger;
    }

    public void logInfo(String message, Exception e, String gatewayId) {
        Properties properties = getTextoPropertiesValues();
        if (properties == null) {
            //throw new IllegalArgumentException("Properties cannot be null");
            System.out.println("Properties cannot be null");
        }
        String logLevelStr = properties.getProperty("log.level").toUpperCase();
        if (logLevelStr.equals("INFO")) { 
            System.out.println(formatMessage(message, gatewayId));
            if (e != null){
                e.printStackTrace();
            }
        }
    }

    public void logWarn(String message, Exception e, String gatewayId) {
        Properties properties = getTextoPropertiesValues();
        if (properties == null) {
            //throw new IllegalArgumentException("Properties cannot be null");
            System.out.println("Properties cannot be null");
        }
        String logLevelStr = properties.getProperty("log.level").toUpperCase();
        if (logLevelStr.equals("WARNING")) { 
            System.out.println(formatMessage(message, gatewayId));
            if (e!= null){
                e.printStackTrace();
            }
        }
    }

    public void logDebug(String message, Exception e, String gatewayId) {
        Properties properties = getTextoPropertiesValues();
        if (properties == null) {
            //throw new IllegalArgumentException("Properties cannot be null");
            System.out.println("Properties cannot be null");
        }
        String logLevelStr = properties.getProperty("log.level").toUpperCase();
        if (logLevelStr.equals("DEBUG")) { 
            System.err.println(formatMessage(message, gatewayId));
            if (e!= null){
                e.printStackTrace();
            }
        }
    }

    public void logError(String message, Exception e, String gatewayId) {
        Properties properties = getTextoPropertiesValues();
        if (properties == null) {
            //throw new IllegalArgumentException("Properties cannot be null");
            System.out.println("Properties cannot be null");
        }
        String logLevelStr = properties.getProperty("log.level").toUpperCase();
        if (logLevelStr.equals("ERROR")) { 
            System.err.println(formatMessage(message, gatewayId));
            if (e!= null){
                e.printStackTrace();
            }
        }
    }

    private String formatMessage(String message, String gatewayId) {
        return (gatewayId == null) ? message : "GTW: " + gatewayId + ": " + message;
    }
    
    public Properties getTextoPropertiesValues() {
        String file = "Texto.properties";
        String directory = USER_HOME + FILE_SEP + "Savics" + FILE_SEP + "DataToCare" + FILE_SEP + "Biopics" + FILE_SEP + "Biopics";
        String location = directory + FILE_SEP + file;
        try {
            File f = new File(location);
            if (!f.exists() || f.isDirectory()) {
                location = file;

                File f2 = new File(location);
                if (!f2.exists() || f2.isDirectory()) {
                    location = USER_DIR + FILE_SEP + "plugins" + FILE_SEP + "texto" + FILE_SEP + file;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Properties properties = new Properties();

        File f = new File(location);
        if (f.exists() && !f.isDirectory()) {
            InputStream input = null;
            try {

                input = new FileInputStream(location);
                // load a properties file
                properties.load(input);
            } catch (IOException ex) {
                java.util.Locale.setDefault(java.util.Locale.ENGLISH);
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }
    
}

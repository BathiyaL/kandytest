package com.ktsapi.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.management.RuntimeErrorException;


// ResourceBundle.getBoundle("<some key>").getString("<some key>");
public class AvtomatProperties {
	private static AvtomatProperties avtomatProperties = null;
    private  Properties properties = null;
	private  Properties implementations = null;
	private final String userDir = System.getProperty("user.dir");
	private final String fileSeparator = System.getProperty("file.separator");
	
	private AvtomatProperties() {
	    String propValue = System.getProperty("propertiesFile");
	    if (propValue == null) throw new RuntimeException("propertiesFile is not defined");
	    properties = loadProperties(propValue);
	    propValue = properties.getProperty("implementationsFile");
	    if (propValue == null) throw new RuntimeException("implementationFile is not defined in properties file");
	    implementations = loadProperties(propValue);
	}
	
	public static AvtomatProperties getTestingProperties() {
		if (avtomatProperties == null) {
		    avtomatProperties = new AvtomatProperties();
		}
		return avtomatProperties;
	}
	
	private Properties loadProperties(String fileName) {
		if (fileName == null) throw new RuntimeException("Missing file name parameter");
		String url = (userDir+fileSeparator+fileName).replace("\\","/");
		Properties p = new Properties();
		try {		
			FileInputStream stream = new FileInputStream(url);
			p.load(stream);
			stream.close();
			return p;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to load "+fileName, e);
		}
	}
	public String getDataFolderPath() {
	    return properties.getProperty("data.path");
	}
	public String getEnvironmentPath() {
	    return properties.getProperty("environmentPath");
	}
	public String getUserDir() {
	    return userDir;
	}
	public String getFileSeparator() {
	    return fileSeparator;
	}
	public Properties getImplementations() {
        return implementations;
    }
    public int getTimeOutGeneral() {
        return Integer.parseInt(properties.getProperty("timeout.general"));
    }
    public String getDataFileExt() {
        return properties.getProperty("data.file.extention");
    }

}

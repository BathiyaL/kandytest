package com.ktsapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ktsapi.webdrivers.KandyWebDriverManager;

public class WindowsRegistryUtils {
	
    public static final String WIN_IE_REG_QUERY = "reg query \"HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Internet Explorer\" /v svcVersion";
    public static final String WIN_FF_REG_QUERY = "reg query \"HKEY_LOCAL_MACHINE\\Software\\Mozilla\\Mozilla Firefox\" /v CurrentVersion";
    public static final String WIN_GC_REG_QUERY = "reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version";
    
    private static final Logger WINDOWS_REGISTRY_LOG = Logger.getLogger(WindowsRegistryUtils.class);
	/*
	 * this is alternative path to get from uninstalll registry list
	 */
	public static String getHKEYQueryOfGC() throws IOException {		
	    Process p = Runtime.getRuntime().exec("reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\"");
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);	    
	    String s = null;
	    String gcDisplayVersionQuery = null;
	    while ((s = stdInput.readLine()) != null) {	    	
	    	if(!s.isEmpty()) {	    		
	    		 String regRootQuery = "reg query " + "\"" + s + "\"" ;
	    		 String regQuery = regRootQuery  + " /v DisplayName" ;	    		 
	    		 Process p2 = Runtime.getRuntime().exec(regQuery);
	    		 if(isRegProcessKeyContainsValue(p2,"Google Chrome")){
	    			 gcDisplayVersionQuery=regRootQuery  + " /v DisplayVersion" ;			 
	    		 }
	    	}	      
	    }	    
		
	    return gcDisplayVersionQuery;
	}
	
	public static boolean isRegProcessKeyContainsValue(Process p,String value) throws IOException {		
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);	    
	    String s = null;
	    while ((s = stdInput.readLine()) != null) {
	    	if(s.contains(value)) {	    		
	    		return true;
	    	}	    	
	    }
	    return false;
	}
	
	
	/*
	 * catch exception when using in real  code
	 */
	public static Map<String, String> getRegistryKeyDataMap(String registryQuery, String registryKey) throws IOException {
		Process p = Runtime.getRuntime().exec(registryQuery);
		Map<String, String> regKeyMap = new HashMap<>();		
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);	    
	    String s = null;	    
	    while ((s = stdInput.readLine()) != null) {
	    	if(s.contains(registryKey)) {
	    		String[] keyDataAry = s.trim().split("    ");
	    		regKeyMap.put("name", keyDataAry[0].trim());
	    		regKeyMap.put("type", keyDataAry[1].trim());
	    		regKeyMap.put("data", keyDataAry[2].trim());
	    	}	    
	    }
	    return regKeyMap;
	}
	
	public static String getWindowsBitVersion() {		
		try {
			if(is64bitversion()) {
				return "win64";
			}else {
				return "win32";
			}
		}catch(Exception e) {
			WINDOWS_REGISTRY_LOG.warn("Error occuer while getting system bit version, hence stick with win32");
			return "win32";
		}

	}
	private static boolean is64bitversion() {
		boolean is64bit = false;
		if (System.getProperty("os.name").contains("Windows")) {
		    is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
		    is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
		}
		return is64bit;
	}
}

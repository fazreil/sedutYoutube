package com.rohaizanroosley.util;

import java.util.prefs.Preferences;

public class UserPreferrences {
	
	static Preferences prefs = Preferences.userNodeForPackage(UserPreferrences.class);

	public static String getDestinationFolder(){
		String result = "";
		final String PREF_NAME = "destinationFolder";

		String defaultValue = System.getProperty("user.dir");
		
		result = prefs.get(PREF_NAME, defaultValue);
		return result;
	}
	
	public static void saveDestinationFolder(String path){
		final String PREF_NAME = "destinationFolder";
		prefs.put(PREF_NAME, path);
	}
	
	public static String getYoutubeDlBinFolder(){
		String result = "";
		final String PREF_NAME = "youtubeDlBinFolder";

		String defaultValue = System.getProperty("user.dir");
		
		if (System.getProperty("os.name").contains("Windows")) {
			defaultValue += "\\bin";
		}else{
			defaultValue = "/usr/local/bin";
		}
		
		result = prefs.get(PREF_NAME, defaultValue);
		return result;
	}	
	
	public static void saveYoutubeDlBinFolder(String path){
		final String PREF_NAME = "youtubeDlBinFolder";
		prefs.put(PREF_NAME, path);
	}
	
	public static String getFfmpegBinFolder(){
		String result = "";
		final String PREF_NAME = "ffmpegBinFolder";
		
		String defaultValue = System.getProperty("user.dir");
		
		if (System.getProperty("os.name").contains("Windows")) {
			defaultValue += "\\bin";
		}else{
			defaultValue = "/usr/local/bin";
		}
		
		result = prefs.get(PREF_NAME, defaultValue);
		return result;
	}	
	
	public static void saveffmpegBinFolder(String path){
		final String PREF_NAME = "ffmpegBinFolder";
		prefs.put(PREF_NAME, path);
	}
}

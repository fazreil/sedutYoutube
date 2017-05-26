package com.rohaizanroosley.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utilities {

	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	public static String prepareYoutubeLink(String yourUrl) {
		String result = "";

		try {
			URL url = new URL(yourUrl);
			
			Map<String, String> query_pairs = splitQuery(url);

			for ( Map.Entry<String, String> entry : query_pairs.entrySet()) {
			    String key = entry.getKey();
			    String val = entry.getValue();

			    if(key.equals("list")){
			    	result = "https://www.youtube.com/playlist?list=" + val;
			    	break;
			    }else{
			    	result = "https://www.youtube.com/watch?v=" + val;
			    }
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error:",
			        JOptionPane.ERROR_MESSAGE);
		}
		
		return result;
	}
	
	public static void updateYoutubeDL(){
		try {
			URL website = new URL("https://yt-dl.org/latest/youtube-dl.exe");
			try (InputStream in = website.openStream()) {
			    Files.copy(in, Paths.get(UserPreferrences.getYoutubeDlBinFolder() + "\\youtube-dl.exe"), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error:",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
}

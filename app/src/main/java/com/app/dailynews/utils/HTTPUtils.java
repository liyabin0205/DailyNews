package com.app.dailynews.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtils {
	
	public static String getStringFromNetwork(String requestUrl){
		
		BufferedReader br = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			if (conn.getResponseCode()==200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String datas = "";
				String line = null;
				while ((line=br.readLine())!=null) {
					datas += line;
				}
				return datas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	} 

}

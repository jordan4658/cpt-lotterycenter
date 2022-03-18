package com.caipiao.live.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

	public static String post(String url) throws IOException {
		URL myURL = new URL(url);
		HttpURLConnection httpsConn = (HttpURLConnection) myURL.openConnection();
		StringBuffer retstr = new StringBuffer();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()))){
			String inputLine;
			while((inputLine = in.readLine()) != null) {
                retstr.append(inputLine);
            }
		}
        return retstr.toString();
	}
}

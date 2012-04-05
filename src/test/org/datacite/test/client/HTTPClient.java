package org.datacite.test.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.datacite.test.model.*;

public class HTTPClient {
	
	private static final int CONNECT_TIMEOUT = 30000;
	private static final int READ_TIMEOUT = 30000;
	
	public HTTPClient(){}
	
	/**
	 * Send an HTTPRequest object to the API.
	 * @param request The HTTPRequest object to send.
	 * @return The HTTPResponse object resulting from the API call.
	 */
	public HTTPResponse makeAPICall(HTTPRequest request){
		HTTPResponse response = new HTTPResponse();
		
		try{			
			URL url = new URL(request.getAddress());
			
			HttpURLConnection conn;
			
			if (url.getProtocol().equalsIgnoreCase("https")){conn = (HttpsURLConnection)url.openConnection();}
			else{conn = (HttpURLConnection)url.openConnection();}
			
			conn.setRequestMethod(request.getMethod());
			
			conn.setDoInput(request.doInput());
			conn.setUseCaches(request.useCaches());
			conn.setDoOutput(request.getBody()!=null);			
			
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);

			if (request.getAccept()!=null){
				conn.setRequestProperty("Accept", request.getAccept());
			}
			
			if (request.getContentType()!=null){
				conn.setRequestProperty("Content-Type", request.getContentType());
			}
			 
			if (request.getUsername()!=null && request.getPassword()!=null){
				Base64 enc = new Base64(-1);			
				String encAuth = enc.encodeToString((request.getUsername()+":"+request.getPassword()).getBytes("UTF-8"));			
				conn.setRequestProperty("Authorization", "Basic "+encAuth);
			}
			        
			if (request.getBody()!=null){
				OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
				osw.write(request.getBody());
				osw.close();
			}
			
			conn.connect();
			response.setResponseCode(conn.getResponseCode());
			        
			InputStreamReader isr;
			if (response.getResponseCode() >= 400){isr = new InputStreamReader(conn.getErrorStream(),"UTF-8");}
			else{isr = new InputStreamReader(conn.getInputStream(),"UTF-8");}
			
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[1024];
			int n;
			while ((n=br.read(buffer))!=-1){
				sb.append(buffer, 0, n);
			}
			
			response.setResponseBody(sb.toString());
			br.close();

		}
		catch(Exception e){
			e.printStackTrace();
			response.setResponseBody(e.toString());
		}
		
		return response;
	}

}

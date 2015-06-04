package org.ussd;
/**
 * Handle api requests to the dhis server
 * 
 * @author Vincent P. Minde
 * 
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

public class DHISAPIHttpRequestor {
	private String auth;
	public DHISAPIHttpRequestor() {
		auth = new String(Base64.encodeBase64("admin:district".getBytes()));
	}

	public String get(String url) {
		return request(url, "GET", null);
	}
	/**
	 * Make request to dhis server
	 * 
	 * @param apiUrl {String} api url
	 * @param method {String} Request Method POST,GET,PUT,DELETE etc
	 * @param data {String} Data to be sent to server. Can be null if none
	 * @return
	 */
	private String request(String apiUrl, String method, String data) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			URL url = new URL("http://127.0.0.1:8080/demo/api/" + apiUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Authorization", "Basic " + auth);
			connection.setRequestProperty("Content-Type","application/json");

			if (data != null) {
				byte[] postData = data.getBytes("UTF-8");
				int    postDataLength = postData.length;
				
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Length",Integer.toString(postDataLength));
				System.out.println(data);
				OutputStream out = connection.getOutputStream();
				out.write(postData);
				out.flush();
				//out.close();
			}

			// Get Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public String post(String url, String event) {
		return request(url, "POST", event);
	}
}

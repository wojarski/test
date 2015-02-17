package com.goeuro.test;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.goeuro.test.model.Place;
import com.goeuro.test.util.TestServiceUtil;

public class TestService {
	final static String SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	
	/**
	 * The method requests places data and transforms it into CVS format
	 * 
	 * @param args should be a single string 
	 * @throws ClientProtocolException
	 * @throws TestServiceException in case when there was a problem 
	 * during requesting or parsing data or during writing to the CSV file.
	 * 
	 */
	
	public static void main(String[] args) throws TestServiceException {
		if(args.length == 0)
			throw new IllegalArgumentException("Please provide a string parameter.");
		
        String placesJson;
		try {
			placesJson = requestPlaces(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestServiceException("Error while requesting data.");
		}
        
        List<Place> places;
		try {
			places = TestServiceUtil.json2Places(placesJson);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestServiceException("Error while parsing JSON response.");
		}
        
        try {
			TestServiceUtil.printCVS(places);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestServiceException("Error while writing to CSV file.");
		}
	}


	/**
	 * @param queryStr query string
	 * @return places data in JSON format
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String requestPlaces(String queryStr) throws ClientProtocolException, IOException, TestServiceException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SERVICE_URL + "" + queryStr);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        
        if(response.getStatusLine().getStatusCode() != 200) {
        	throw new TestServiceException("Error while requesting data.");
        }
		try {
            HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} finally {
			response.close();
        }
	}
}

package com.gestion.model.geonamesapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpGeonames {

	
	
	public String search(String searchstring) throws FileNotFoundException{
		
		String URL;// = "http://api.geonames.org/search?q="+searchstring+"&lang=fr&type=rdf&username=gentringere";
		URL ="http://api.geonames.org/search?q="+searchstring+"&name_equals="+searchstring+"&type=rdf&username=gentringere";
		String xml="";
		try 
		{
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileOutputStream fop = null;
		File file;
 
		try {
 
			file = new File("/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/geonames-data/"+searchstring+"http.rdf");
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = xml.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		// return XML
	    	  System.out.println(URL);
		return xml;
	}
	
}

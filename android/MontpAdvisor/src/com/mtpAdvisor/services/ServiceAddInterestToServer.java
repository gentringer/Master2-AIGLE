package com.mtpAdvisor.services;



import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.parsers.JSONParser;
import com.mtpAdvisor.parsers.XMLParser;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ServiceAddInterestToServer extends IntentService{

	InterestDbHelper db = new InterestDbHelper(this);

	public static String URL ="";
	static final String KEY_OSM = "osm"; // parent node
	static final String KEY_NODE = "node";
	static final String KEY_TAG = "tag";
	public static String category = Activity_Fragments.inputString;


	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	public ServiceAddInterestToServer() {
		super("ServiceGetBar");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(policy);
		System.out.println("start service add interest to server");

		if(category.equals("Bars/Pubs")){
			category="bars";
		}
		if(category.equals("Hôtels") || category.equals("Hotels")){
			category="hotels";
		}
		if(category.equals("restaurant")){
			category="restaurant";
		}

		XMLParser parser = new XMLParser();
		Interest interst = null;
		Bundle extras = intent.getExtras(); 
		if(extras == null)
			Log.d("Service","null");
		else{
			interst = intent.getExtras().getParcelable("interestfrommap");

		}


		if(interst.getNameInterest()==null)
		{
			//Log.d("null["+i+"]",ba.getLat()+" - "+ba.getLon()+" - "+ba.getName());
		}
		else
		{
			//b.addBar(ba);
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> paramss = new ArrayList<NameValuePair>();
			paramss.add(new BasicNameValuePair("category", category));
			paramss.add(new BasicNameValuePair("nameinterest", interst.getNameInterest()));
			paramss.add(new BasicNameValuePair("lat", interst.getLat()));
			paramss.add(new BasicNameValuePair("lon", interst.getLon()));

			jsonParser.makeHttpRequest("http://gillesentringer.com/php/create_interest.php"
					,"POST", paramss);

		}

		publishResults();



		this.stopSelf();
	}

	private void publishResults() {
		//	Intent intent = new Intent("barsfromosm");
		//
		//	sendBroadcast(intent);
		//	
		//}
	}


}

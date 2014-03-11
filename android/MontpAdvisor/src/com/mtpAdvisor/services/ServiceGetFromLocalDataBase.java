package com.mtpAdvisor.services;

import java.util.ArrayList;

import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ServiceGetFromLocalDataBase extends IntentService{


	InterestDbHelper db = new InterestDbHelper(this);

	static final String KEY_OSM = "osm"; // parent node
	static final String KEY_NODE = "node";
	static final String KEY_TAG = "tag";


	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


	public ServiceGetFromLocalDataBase() {
		super("ServiceGetFromLocalDataBase");
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onHandleIntent(Intent intent) {

		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(policy);
		//System.out.println("start service get from  database");
		String category="";

		ArrayList<Interest> existinginterest = new ArrayList<Interest>();
		Bundle extras = intent.getExtras(); 
		if(extras == null)
			Log.d("intent","null");
		else{
			category = intent.getStringExtra("category");
			if(category.equals("Bars/Pubs")){
				category="bars";
			}
			if(category.equals("Hôtels") || category.equals("Hotels")){
				category="hotels";
				

			}

		}
		
		existinginterest= db.getAllInsterests(category);
		publishResults(existinginterest);
		this.stopSelf();

	}


	private void publishResults(ArrayList<Interest> al2) {
		Intent intent = new Intent("LOCAL_DB");
//		
		intent.putParcelableArrayListExtra("existingbars", al2);
		sendBroadcast(intent);

	}

}

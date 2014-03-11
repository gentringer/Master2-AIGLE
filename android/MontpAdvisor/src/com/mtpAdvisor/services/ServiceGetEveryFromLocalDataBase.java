package com.mtpAdvisor.services;

import java.util.ArrayList;

import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ServiceGetEveryFromLocalDataBase extends IntentService{


	InterestDbHelper db = new InterestDbHelper(this);

	static final String KEY_OSM = "osm"; // parent node
	static final String KEY_NODE = "node";
	static final String KEY_TAG = "tag";


	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


	public ServiceGetEveryFromLocalDataBase() {
		super("ServiceGetAllInterestFromServer");
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onHandleIntent(Intent intent) {

		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(policy);
		//System.out.println("start service get from  database");

		ArrayList<Interest> existinginterest = new ArrayList<Interest>();
		
		
		existinginterest= db.getEveryINterest();
		publishResults(existinginterest);
		this.stopSelf();

	}


	private void publishResults(ArrayList<Interest> al2) {
		Intent intent = new Intent("LOCAL_EXIST");
//		
		intent.putParcelableArrayListExtra("all_exists", al2);
		sendBroadcast(intent);

	}

}

package com.mtpAdvisor.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.projet.R;
import com.mtpAdvisor.activites.ListCateogry;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.fragments.Fragment_InterestList;

public class ProximityService extends Service{
	String proximitysd = "com.apps.ProximityService";
	int n = 0;
	private BroadcastReceiver mybroadcast;
	private LocationManager locationManager;
	MyLocationListener locationListenerp;

	public ProximityService() {

	}


	@Override
	public void onCreate() {
		mybroadcast = new ProximityIntentReceiver();
		locationManager = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);


		double lat;
		double lng;
		float radius = 100;
		long expiration = -1;
		InterestDbHelper db = new InterestDbHelper(this);
		Cursor cursor;
		List<String> likes = db.getEveryLike(ListCateogry.currentUser.getUserid());

		List<Interest> interestlist = new ArrayList<Interest>();
		
		interestlist = ListCateogry.inter;
		for(Interest inte : ListCateogry.inter){
			for(String s : likes){
				if(s.equals(inte.getNameInterest())){

					System.out.println("create alert");
					//			lat = cursor.getInt(MyDBAdapter.LATITUDE_COLUMN)/1E6;
					//			lng = cursor.getInt(MyDBAdapter.LONGITUDE_COLUMN)/1E6;
					//			String what = cursor.getString(MyDBAdapter.ICON_COLUMN);
					//			String how = cursor.getString(MyDBAdapter.FISH_COLUMN);
					lat= Double.parseDouble(inte.lat) ;
					lng = Double.parseDouble(inte.lon) ;
					String what =inte.getNameInterest();
					String how = inte.getCategory();
					String proximitys = "com.apps.ProximityService" + n;
					IntentFilter filter = new IntentFilter(proximitys);
					registerReceiver(mybroadcast, filter );

					Intent intent = new Intent(proximitys);

					intent.putExtra("alert", what);
					intent.putExtra("type", how);
					PendingIntent proximityIntent = PendingIntent.getBroadcast(this, n, intent, PendingIntent.FLAG_CANCEL_CURRENT);
					locationManager.addProximityAlert(lat, lng, radius, expiration, proximityIntent);
					//sendBroadcast(new Intent(intent));
					n++;
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		//	Toast.makeText(this, "Proximity Service Stopped", Toast.LENGTH_LONG).show();
		try{
			unregisterReceiver(mybroadcast);
		}catch(IllegalArgumentException e){
			Log.d("reciever",e.toString());
		}


	}
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "Proximity Service Started", Toast.LENGTH_LONG).show();
		//IntentFilter filter = new IntentFilter(proximitys);
		//registerReceiver(mybroadcast,filter);

	}

	public class ProximityIntentReceiver extends BroadcastReceiver{
		private int NOTIFICATION_ID = 1000;
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String key = LocationManager.KEY_PROXIMITY_ENTERING;

			Boolean entering = arg1.getBooleanExtra(key, false);
			String here = arg1.getExtras().getString("alert");
			String happy = arg1.getExtras().getString("type");

			NotificationManager notificationManager = 
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			PendingIntent pendingIntent = PendingIntent.getActivity(arg0, 0, arg1, 0);        

			Notification notification = createNotification();

			notification.setLatestEventInfo(arg0, 
					"MontpAdvisor!!", getString(R.string.proximityalert)+" "+happy+" " + here , pendingIntent);

			notificationManager.notify(NOTIFICATION_ID, notification);
			NOTIFICATION_ID++;

		}

		private Notification createNotification() {
			Notification notification = new Notification();

			notification.icon = R.drawable.logo_mont_advisor;
			notification.when = System.currentTimeMillis();

			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;

			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notification.defaults |= Notification.DEFAULT_LIGHTS;

			notification.ledARGB = Color.WHITE;
			notification.ledOnMS = 1500;
			notification.ledOffMS = 1500;


			return notification;
		}
		//make actions



	}
	public class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			Toast.makeText(getApplicationContext(), "I was here", Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String s) {
		}
		public void onProviderEnabled(String s) {            
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
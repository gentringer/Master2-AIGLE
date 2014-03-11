package com.mtpAdvisor.activites;

// "PopUp lorsqu'on clique sur la loupe "
import com.example.projet.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class MapActivitySearch extends Activity {

	// Google Map
	private GoogleMap googleMap;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_search);

		WindowManager.LayoutParams params = getWindow().getAttributes();

		params.height = 1500;
		params.width = 1000;

		this.getWindow().setAttributes(params);

		Bundle objetbunble  = this.getIntent().getBundleExtra("KEY_BUNDLE");
		//On r�cup�re les donn�es du Bundle
		if(objetbunble == null)
		{
			//Log.d("objectBundle", "it's null");
		}
		else
		{
			//Log.d("objectBundle", "it's not null");
		}
		String latitude  = this.getIntent().getBundleExtra("KEY_BUNDLE").getString(ListCateogry.keyLatitude);
		String longitude = this.getIntent().getBundleExtra("KEY_BUNDLE").getString(ListCateogry.keyLongitude);
		String nom = this.getIntent().getBundleExtra("KEY_BUNDLE").getString("name");
		String adresse = this.getIntent().getBundleExtra("KEY_BUNDLE").getString("adresse");

		try {
			// Loading map
			initilizeMap();

			System.out.println("nom: " + nom);

			CameraPosition cameraPosition = new CameraPosition.Builder().target(
					new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).zoom(14).build();

			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			// create marker
			MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).title(nom);
			marker.snippet(adresse);

			// BLUE color icon
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

			// adding marker
			googleMap.addMarker(marker);


			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


			//Showing Current Location
			googleMap.setMyLocationEnabled(true); // false to disable

			//Zooming Buttons
			googleMap.getUiSettings().setZoomControlsEnabled(true); // true to enable

			//Zooming Functionality (enable functionality or disable it)
			googleMap.getUiSettings().setZoomGesturesEnabled(true); // true to enable

			//Compass Functionality (Compass == Boussole)
			googleMap.getUiSettings().setCompassEnabled(true);

			//My Location Button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			//Map Rotate Gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

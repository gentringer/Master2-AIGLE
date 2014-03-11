package com.mtpAdvisor.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.gesture.OrientedBoundingBox;
import android.text.Editable;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.projet.*;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.activites.ListCateogry;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.services.ServiceAddInterestToServer;
import com.mtpAdvisor.services.ServiceAddToLocalDataBase;
import com.mtpAdvisor.services.ServiceGetAllInterestFromServer;

public class Fragment_Map extends Fragment implements OnMapLongClickListener
{

	// Google Map
	public List<Interest> barlist =  new ArrayList<Interest>() ;
	public static List<String> favoritelist = new ArrayList<String>();

	private static View v;


	private SupportMapFragment fragment;
	private GoogleMap googleMap;

	public static void removeFavorite(String name){
		if(favoritelist.contains(name)){
			favoritelist.remove(name);
		}
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tab_googlemap, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentManager fm = getFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		

		if (googleMap == null) {

			//System.out.println("googleMap was null");
			googleMap = fragment.getMap();
			googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
			initilizeMap();
    
			double latdef = 43.607049;
			double longdef = 3.870835;

			CameraPosition cameraPosition = new CameraPosition.Builder().target(
					new LatLng(latdef, longdef)).zoom(13).build();

			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


			googleMap.setMyLocationEnabled(true); 

			googleMap.getUiSettings().setZoomControlsEnabled(true);  

			googleMap.getUiSettings().setZoomGesturesEnabled(true); 

			googleMap.getUiSettings().setCompassEnabled(true);

			googleMap.setOnMapLongClickListener(this);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			googleMap.getUiSettings().setRotateGesturesEnabled(true);
		}
		else{
		}
	}

	private void initilizeMap() 
	{
		if (googleMap == null) 
		{
			googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();


		}
	}

	

	public static Fragment_Map init(int val) {
		// TODO Auto-generated method stub
		Fragment_Map truitonFrag = new Fragment_Map();
		// Supply val input as an argument.
		Bundle args = new Bundle();
		args.putInt("val", val);
		truitonFrag.setArguments(args);
		return truitonFrag;
	}
	
	



	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//System.out.println("mapCreated");
		IntentFilter filter = new IntentFilter("news");
		IntentFilter filter2 = new IntentFilter("addBars");

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(getnews, filter);
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(addBars, filter2);

		Bundle bundle = this.getArguments();
		if(bundle!=null){
		String mystring = bundle.getString("name");
		}
		
		
	}
	
	private BroadcastReceiver getnews = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			googleMap.clear();
			//googleMap=null;
			//onResume();
			for(Interest b: barlist){
				double latitude = Double.parseDouble(b.lat);
				double longitude = Double.parseDouble(b.lon);
				String name = b.nameInterest;
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);
				marker.snippet(b.adresse);
			
				if(favoritelist.contains(name)){
					marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

				}
				else{
					marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

				}

				googleMap.addMarker(marker);
			}
			

		}


	};
	
	private BroadcastReceiver addBars = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			barlist = Fragment_InterestList.interestlist;
			//System.out.println("add bars receveier"+barlist.size());
			for(Interest b: barlist){
				double latitude = Double.parseDouble(b.lat);
				double longitude = Double.parseDouble(b.lon);
				String name = b.nameInterest;
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);
				marker.snippet(b.adresse);
			
				if(favoritelist.contains(name)){
					marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

				}
				else{
					marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

				}

				googleMap.addMarker(marker);
			}
			
			LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this);


		}


	};

	@Override
	public void onMapLongClick(final LatLng point) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);

		alert.setTitle("Ajouter nouveau "+Activity_Fragments.inputString);
		alert.setIcon(android.R.drawable.btn_plus);

		final TextView adressetext = new TextView(getActivity());
		adressetext.setText("Saisir adresse");
		// Set an EditText view to get user input 
		final EditText adresse = new EditText(getActivity());
		adresse.setHint("adresse");
		layout.addView(adressetext);
		layout.addView(adresse);

		final TextView nomtext = new TextView(getActivity());
		nomtext.setText("Nom :");

		final EditText nom = new EditText(getActivity());
		nom.setHint("nom");
		layout.addView(nomtext);
		layout.addView(nom);
		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String adresseGeocoder = null;
		for(Address adr:addresses){
			adresseGeocoder=adr.getAddressLine(0);
		}
		adresse.setText(adresseGeocoder);

		alert.setView(layout);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable adre = adresse.getText();
				Editable name = nom.getText();


				Interest inte = new Interest();
				inte.setNameInterest(name.toString());
				inte.setCategory(Activity_Fragments.inputString);
				inte.setLat(String.valueOf(point.latitude));
				inte.setLon(String.valueOf(point.longitude));
				inte.adresse=adresse.getText().toString();
						
				Intent serviceAddtoServer = new Intent(getActivity(),ServiceAddInterestToServer.class); 
				serviceAddtoServer.putExtra("interestfrommap", inte);
				getActivity().startService(serviceAddtoServer);
				
				ArrayList<Interest> singleListe = new ArrayList<Interest>();
				singleListe.add(inte);
				
				Intent serviceaddtolocaldb = new Intent(getActivity(),ServiceAddToLocalDataBase.class); 
				serviceaddtolocaldb.putParcelableArrayListExtra("newinterest", singleListe);
				getActivity().startService(serviceaddtolocaldb);

				// Do something with value!
				String lat = String.valueOf(point.latitude);
				String lon = String.valueOf(point.longitude);
				Toast.makeText(getActivity(), point.toString(), Toast.LENGTH_LONG).show();
				Fragment_InterestList.interestlist.add(new Interest(Activity_Fragments.inputString,lat,lon,name.toString(),adre.toString()));
				Fragment_InterestList.settingAdapter();
				ListCateogry.inter.add(new Interest(Activity_Fragments.inputString,lat,lon,name.toString(),adre.toString()));
				ListCateogry.settingAdapter();
				Collections.sort(Fragment_InterestList.interestlist, new Comparator<Interest>(){
					public int compare(Interest s1, Interest s2) {
						return s1.getNameInterest().compareToIgnoreCase(s2.getNameInterest());
					}
				});
				
				double latitude = point.latitude;
				double longitude = point.longitude;
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(inte.getNameInterest());
				marker.snippet(inte.adresse);
				// BLUE color icon
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

				// adding marker
				googleMap.addMarker(marker);
				googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();

	}


}

package com.mtpAdvisor.fragments;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;



import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.UserSettingsFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.projet.*;

import com.mtpAdvisor.Facebook.FacebookActivity;
import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.adapters_listeners.Adapteur;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.classes.User;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.services.ProximityService;
import com.mtpAdvisor.services.ServiceAddToLocalDataBase;
import com.mtpAdvisor.services.ServiceGetAllInterestFromServer;
import com.mtpAdvisor.services.ServiceConnectOSM;
import com.mtpAdvisor.services.ServiceGetFromLocalDataBase;
import com.mtpAdvisor.splash.SplashScreen;
import com.mtpAdvisor.userMenu.MenuActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;


import android.net.Uri;
import android.os.Bundle;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Fragment_InterestList extends ListFragment  {

	protected static InterestDbHelper db;
	private ImageButton imgAdd = null;
	public String categorystring;
	public static User currentUser = new User();
	protected ProgressDialog mProgressDialog;
	public AutoCompleteTextView autoComplete;

	public static  EditText inputSearch;
	public static ListView listView;

	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private MenuItem fav;

	public static List<Interest> interestlist = new ArrayList<Interest>();

	public Fragment_InterestList() {

	}



	// Récupération des informations facebook
	private String buildUserInfoDisplay(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");


		userInfo.append(String.format(mActivity.getString(R.string.hello)+" : %s\n\n", 
				user.getName()));

		return userInfo.toString();
	}

	// On requete le profil facebook de l'utilisateur
	private void makeMeRequest(final Session session) {
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (session == Session.getActiveSession()) {
					if (user != null) {
						String builder = buildUserInfoDisplay(user);
						profilePictureView.setProfileId(user.getId());
						userNameView.setText(builder);
						currentUser = new User(user.getId(),user.getName());
						if(!db.ExistsUser(user.getId())){
							db.addUser(currentUser);
						}
					}
				}
				if (response.getError() != null) {
					handleError(response.getError());
				}
			}

		});

		Request friendRequest = Request.newMyFriendsRequest(session, 
				new GraphUserListCallback(){
			@Override
			public void onCompleted(List<GraphUser> users,
					Response response) {
				String friendstring = "";
				for(GraphUser graph : users){
					friendstring += graph.getName();
					friendstring +=" , ";
				}
				//friendsView.setText(friendstring);
			}


		});


		friendRequest.executeAsync();

		request.executeAsync();

	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Instanciation du DbHelper
		db = new InterestDbHelper(this.mActivity);

		setHasOptionsMenu(true);

	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		fav = menu.add(mActivity.getString(R.string.preferences)+" "+ Activity_Fragments.inputString);
		fav.setIcon(R.drawable.action_people);
		fav.setEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			FacebookActivity.menuint=true;

			Intent i = new Intent(mActivity, MenuActivity.class);
			startActivity(i);
			return true;
			// Not implemented here
		default:
			break;
		}

		return false;
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_list, container, false);

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.tab_list, container, false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}

		profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		//friendsView = (TextView) view.findViewById(R.id.friends);
		init(savedInstanceState);
		//	addListenerOnSpinnerItemSelection(view);

		//IntentFilter filter = new IntentFilter("NEW_BARS");
		IntentFilter filter2 = new IntentFilter("LOCAL_DB");
		//IntentFilter filter3 = new IntentFilter("barsfromosm");

		//mActivity.getApplicationContext().registerReceiver(myReceiver, filter);
		mActivity.getApplicationContext().registerReceiver(LocalDataBaseRecevier, filter2);
		//mActivity.getApplicationContext().registerReceiver(GetFromDatabase, filter3);




		return view;
	}

	//Receiver pour BDD locale 
	private BroadcastReceiver LocalDataBaseRecevier = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			ArrayList<Interest> barslocal;
			ArrayList<String> names = new ArrayList<String>();


			//bars = intent.getParcelableArrayListExtra("listebar");
			barslocal=intent.getParcelableArrayListExtra("existingbars");

			for(Interest b : barslocal){
				names.add(b.nameInterest);
			}

			//Si BDD locale est vide
			if(barslocal.size()==0){
				mActivity.startService(new Intent(mActivity,ServiceGetAllInterestFromServer.class));
			}
			else{

				interestlist = barslocal;

				mProgressDialog.dismiss();

				listView = (ListView) getActivity().findViewById(android.R.id.list);  

				Intent i= new Intent("addBars");

				LocalBroadcastManager.getInstance(context).sendBroadcast(i);
				ArrayList<String> nameinterest = new ArrayList<String>();
				for(Interest inter : interestlist){
					nameinterest.add(inter.getNameInterest());
				}

				Collections.sort(interestlist, new Comparator<Interest>(){
					public int compare(Interest s1, Interest s2) {
						return s1.getNameInterest().compareToIgnoreCase(s2.getNameInterest());
					}
				});

				final Adapteur adapter = new Adapteur(mActivity, interestlist, db);

				inputSearch = (EditText) getActivity().findViewById(R.id.inputSearch);
				String search = mActivity.getString(R.string.rechercher) +" "+Activity_Fragments.inputString;
				inputSearch.setHint(search);
				listView.setAdapter(adapter);

				inputSearch.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {

						adapter.getFilter().filter(s);
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});

			}

			mActivity.getApplicationContext().unregisterReceiver(this);

		}


	};

	public static void settingAdapter(){
		final Adapteur adapter = new Adapteur(mActivity, interestlist, db);

		inputSearch = (EditText) mActivity.findViewById(R.id.inputSearch);
		
		listView.setAdapter(adapter);
		String search = mActivity.getString(R.string.rechercher) +" "+Activity_Fragments.inputString;
		inputSearch.setHint(search);
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	private static Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	//Lorsqu'on a trouvé des données dans la bdd distante
	/*private BroadcastReceiver GetFromDatabase = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			mActivity.startService(new Intent(mActivity,ServiceGetAllInterestFromServer.class));
			mActivity.getApplicationContext().unregisterReceiver(this);

		}



	};

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			ArrayList<Interest> bars;
			ArrayList<String> names = new ArrayList<String>();

			//bars = intent.getParcelableArrayListExtra("listebar");
			bars=intent.getParcelableArrayListExtra("newbars");
			for(Interest b : bars){
				names.add(b.nameInterest);
			}

			if(bars.size()>0){
				sendDataToDatbase(bars);

				interestlist = bars;

				ListAdapter listAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, names);

				Intent i= new Intent("addBars");
				LocalBroadcastManager.getInstance(context).sendBroadcast(i);

				listView = (ListView) getActivity().findViewById(android.R.id.list);  

				final Adapteur adapter = new Adapteur(mActivity, interestlist, db);
				inputSearch = (EditText) getActivity().findViewById(R.id.inputSearch);
				listView.setAdapter(adapter);
				inputSearch.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {

						adapter.getFilter().filter(s);
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});

				mProgressDialog.dismiss();



			}
			else if (bars.size()==0){
				mActivity.startService(new Intent(mActivity,ServiceConnectOSM.class));

			}
			mActivity.getApplicationContext().unregisterReceiver(this);

		}
	};*/

	private void sendDataToDatbase(ArrayList<Interest> bars) {
		// TODO Auto-generated method stub
		Intent serviceIntent = new Intent(mActivity,ServiceAddToLocalDataBase.class); 
		serviceIntent.putParcelableArrayListExtra("newinterest", bars);

		mActivity.startService(serviceIntent);
	}

	@Override
	public void onActivityCreated(Bundle savedinstance){
		super.onActivityCreated(savedinstance);

		Fragment fragment;
		FragmentManager fm = getFragmentManager();
		fragment = (Fragment) fm.findFragmentById(R.id.tablist);
		//if (fragment == null) {

		categorystring = Activity_Fragments.inputString;


		Intent localdataintent = new Intent(mActivity,ServiceGetFromLocalDataBase.class);
		localdataintent.putExtra("category", categorystring);
		mActivity.startService(localdataintent);
		mProgressDialog = ProgressDialog.show(getActivity(), "Veuillez patienter",
				"Chargement des "+Activity_Fragments.inputString, true);

	}

	private void init(Bundle savedInstanceState) {

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {

			makeMeRequest(session);

		}

	}




	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {

		Toast.makeText(mActivity, getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

	}




	private static final Uri M_FACEBOOK_URL = Uri.parse("http://m.facebook.com");
	public static  List<String> favoriteList = new ArrayList<String>();

	private void handleError(FacebookRequestError error) {
		DialogInterface.OnClickListener listener = null;
		String dialogBody = null;

		if (error == null) {
			//     dialogBody = getString(R.string.error_dialog_default_text);
		} else {
			switch (error.getCategory()) {
			case AUTHENTICATION_RETRY:
				// tell the user what happened by getting the message id, and
				// retry the operation later
				String userAction = (error.shouldNotifyUser()) ? "" :
					getString(error.getUserActionMessageId());
				//       dialogBody = getString(R.string.error_authentication_retry, userAction);
				listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(Intent.ACTION_VIEW, M_FACEBOOK_URL);
						startActivity(intent);
					}
				};
				break;

			case AUTHENTICATION_REOPEN_SESSION:
				// close the session and reopen it.
				//    dialogBody = getString(R.string.error_authentication_reopen);
				listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Session session = Session.getActiveSession();
						if (session != null && !session.isClosed()) {
							session.closeAndClearTokenInformation();
						}
					}
				};
				break;

			case PERMISSION:
				// request the publish permission
				dialogBody = "error";
				listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//       pendingAnnounce = true;
						//       requestPublishPermissions(Session.getActiveSession());
					}
				};
				break;

			case SERVER:
			case THROTTLING:
				// this is usually temporary, don't clear the fields, and
				// ask the user to try again
				//    dialogBody = getString(R.string.error_server);
				break;

			case BAD_REQUEST:
				// this is likely a coding error, ask the user to file a bug
				//  dialogBody = getString(R.string.error_bad_request, error.getErrorMessage());
				break;

			case OTHER:
			case CLIENT:
			default:
				// an unknown issue occurred, this could be a code error, or
				// a server side issue, log the issue, and either ask the
				// user to retry, or file a bug
				//     dialogBody = getString(R.string.error_unknown, error.getErrorMessage());
				break;
			}
		}

		new AlertDialog.Builder(mActivity)
		.setPositiveButton("error", listener)
		.setTitle("error")
		.setMessage(dialogBody)
		.show();
	}





}

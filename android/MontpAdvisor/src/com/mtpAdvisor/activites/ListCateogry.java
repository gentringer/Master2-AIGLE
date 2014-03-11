package com.mtpAdvisor.activites;

import com.example.projet.R;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.model.GraphUser;
import com.mtpAdvisor.adapters_listeners.Adapteur;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.classes.User;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.services.ProximityService;
import com.mtpAdvisor.services.ServiceAddToLocalDataBase;
import com.mtpAdvisor.services.ServiceGetAllInterestFromServer;
import com.mtpAdvisor.services.ServiceConnectOSM;
import com.mtpAdvisor.services.ServiceGetEveryFromLocalDataBase;
import com.mtpAdvisor.services.ServiceGetEveryInterestFromServer;
import com.mtpAdvisor.services.ServiceGetFromLocalDataBase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

// Liste des catégories
public class ListCateogry extends Activity {


	private ListView maListViewPerso;
	private EditText searchEditText;
	private ImageButton searchButton;

	public static final String keyLatitude = "KEY_LATITUDE";
	public static final String keyLongitude = "KEY_LONGITUDE";
	public static ArrayList<Interest> inter = new ArrayList<Interest>();
	public ArrayList<String> names = new ArrayList<String>();
	// Montpellier
	private final double LAT_CNTR_MONTP = 43.610817;
	private final double LONG_CNTR_MONTP = 3.868677;
	protected ProgressDialog mProgressDialog;
	public static User currentUser = new User();
	protected static InterestDbHelper db;
	private static final Uri M_FACEBOOK_URL = Uri.parse("http://m.facebook.com");


	public static void stopservice(){
		mActivity.stopService(new Intent(mActivity,ProximityService.class));
	}
	public static void startService(){
		mActivity.startService(new Intent(mActivity,ProximityService.class));
	}

	private static Activity mActivity;


	private void init(Bundle savedInstanceState) {

		Session session = Session.getActiveSession();
		mActivity = this;
		if (session != null && session.isOpened()) {

			makeMeRequest(session);

		}


	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(savedInstanceState);

		setContentView(R.layout.activity_list_cateogry);
		db = new InterestDbHelper(this);

		//Récupération de la listview créée dans le fichier main.xml
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);
		IntentFilter filter2 = new IntentFilter("LOCAL_EXIST");
		IntentFilter filter = new IntentFilter("ALLINTERESTS");
		IntentFilter filter3 = new IntentFilter("barsfromosm");

		getBaseContext().registerReceiver(myReceiverEveryInterestLocalDB, filter2);
		getBaseContext().registerReceiver(myReceiver, filter);
		getBaseContext().registerReceiver(oSMReceiver, filter3);
		getBaseContext().startService(new Intent(getBaseContext(),ServiceGetEveryFromLocalDataBase.class));

		mProgressDialog = ProgressDialog.show(ListCateogry.this, "Veuillez patienter",
				"Chargement des intérêts", true);

		//Création de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		//On déclare la HashMap qui contiendra les informations pour un item
		HashMap<String, String> map;

		//Création d'une HashMap pour insérer les informations du premier item de notre listView
		map = new HashMap<String, String>();
		
		//on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
		map.put("titre", getString(R.string.hotel));
		//on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
		map.put("description", getString(R.string.hoteldescription));
		//on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
		map.put("img", String.valueOf(R.drawable.hotel));
		//enfin on ajoute cette hashMap dans la arrayList
		listItem.add(map);

		//On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView
		map = new HashMap<String, String>();
		map.put("titre", getString(R.string.restaurant));
		map.put("description", getString(R.string.restaurantdescription));
		map.put("img", String.valueOf(R.drawable.action_eating));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre", getString(R.string.bars));
		map.put("description", getString(R.string.bardescription));
		map.put("img", String.valueOf(R.drawable.bars));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre", getString(R.string.parking));
		map.put("description", getString(R.string.parkingdescription));
		map.put("img", String.valueOf(R.drawable.parking));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre",  getString(R.string.monument));
		map.put("description", getString(R.string.monumentdescription));
		map.put("img", String.valueOf(R.drawable.logo_monument));
		listItem.add(map);

		//Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(this, listItem, R.layout.category_list_row,
				new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});


		//On attribut à notre listView l'adapter que l'on vient de créer
		maListViewPerso.setAdapter(mSchedule);

		//Enfin on met un écouteur d'évènement sur notre listView
		maListViewPerso.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
				//on créer une boite de dialogue
				AlertDialog.Builder adb = new AlertDialog.Builder(ListCateogry.this);
				//on attribut un titre à notre boite de dialogue
				adb.setTitle(map.get("titre"));
				//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
				adb.setMessage("Description : "+map.get("description"));
				//on indique que l'on veut le bouton ok à notre boite de dialogue
				adb.setPositiveButton("Ok", null);
				//on affiche la boite de dialogue
				//	adb.show();

				// Démarrage d'une nouvelle activité (ChosseCategory) qui va s'occuper de transmettre la bonne information à la liste et aux services



				Intent intentcategory = new Intent(ListCateogry.this,ChooseCategory.class);
				intentcategory.putExtra("category", map.get("titre"));
				startActivity(intentcategory);
			}
		});

	}

	private void sendDataToDatbase(ArrayList<Interest> bars) {
		// TODO Auto-generated method stub
		Intent serviceIntent = new Intent(ListCateogry.this,ServiceAddToLocalDataBase.class); 
		serviceIntent.putParcelableArrayListExtra("newinterest", bars);

		startService(serviceIntent);
	}



	private BroadcastReceiver oSMReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			mActivity.startService(new Intent(mActivity,ServiceGetEveryInterestFromServer.class));
			unregisterReceiver(this);

		}



	};



	private BroadcastReceiver myReceiverEveryInterestLocalDB = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			inter = intent.getParcelableArrayListExtra("all_exists");

			if(inter.size()>0){
				mProgressDialog.dismiss();


				for(Interest b : inter){
					names.add(b.nameInterest);
				}


				final ArrayList<Interest> finalinter = inter;
				final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autocompletion);


				ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),
						android.R.layout.simple_dropdown_item_1line, names);

				autoComplete.setAdapter(adapter);

				searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);

				searchButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String searchedItem = autoComplete.getText().toString();

						for(Interest intere : finalinter){
							if(intere.getNameInterest().equals(searchedItem)){
								Bundle objBun = new Bundle();

								objBun.putString(keyLatitude, intere.getLat());
								objBun.putString(keyLongitude, intere.getLon());
								objBun.putString("name", intere.getNameInterest());
								objBun.putString("adresse", intere.adresse);

								Intent i = new Intent(ListCateogry.this, MapActivitySearch.class);
								i.putExtra("KEY_BUNDLE", objBun);
								startActivity(i);
							}
						}

					}
				});



			}

			getBaseContext().startService(new Intent(getBaseContext(),ServiceGetEveryInterestFromServer.class));
			unregisterReceiver(this);
		}


	};

	private BroadcastReceiver myReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			ArrayList<Interest> inter2;
			ArrayList<Interest> newinteret = new ArrayList<Interest>();

			ArrayList<String> names = new ArrayList<String>();

			//bars = intent.getParcelableArrayListExtra("listebar");
			inter2=intent.getParcelableArrayListExtra("everyInterest");


			if(inter2.size()==0){
				mActivity.startService(new Intent(mActivity,ServiceConnectOSM.class));
			}
			else{
				mProgressDialog.dismiss();

				for(Interest in : inter2){
					boolean test = false;
					for(Interest in2 : inter){
						if(in2.getNameInterest().equals(in.getNameInterest())){
							test = true;
						}
						
					}
					if(!test){
						newinteret.add(in);
					}
				}

				sendDataToDatbase(newinteret);
				for(Interest b : newinteret){

					names.add(b.nameInterest);
					settingAdapter();
				}

				if(inter.size()==0){
					mProgressDialog.dismiss();


					for(Interest b : inter2){
					//	names.add(b.nameInterest);
					}


					final ArrayList<Interest> finalinter = inter2;
					final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autocompletion);


					ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_dropdown_item_1line, names);

					autoComplete.setAdapter(adapter);

					searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);

					searchButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String searchedItem = autoComplete.getText().toString();

							for(Interest intere : finalinter){
								if(intere.getNameInterest().equals(searchedItem)){
									Bundle objBun = new Bundle();

									objBun.putString(keyLatitude, intere.getLat());
									objBun.putString(keyLongitude, intere.getLon());
									objBun.putString("name", intere.getNameInterest());
									objBun.putString("adresse", intere.adresse);

									Intent i = new Intent(ListCateogry.this, MapActivitySearch.class);
									i.putExtra("KEY_BUNDLE", objBun);
									startActivity(i);
								}
							}

						}
					});



				}
				else{
					
				}


				unregisterReceiver(this);

			}

		}



	};

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;g
	}*/

	// Récupération des informations facebook
	private String buildUserInfoDisplay(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");


		userInfo.append(String.format("Hello : %s\n\n", 
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
						currentUser = new User(user.getId(),user.getName());
						if(db.ExistsUser(user.getId())){	
							db.addUser(currentUser);
						}
						startService();
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

		new AlertDialog.Builder(this)
		.setPositiveButton("error", listener)
		.setTitle("error")
		.setMessage(dialogBody)
		.show();
	}
	
	public static void settingAdapter(){
		ArrayList<String> names = new ArrayList<String>();
		
		for(Interest b : inter){
			if(!names.contains(b.nameInterest))
			names.add(b.nameInterest);
		}


		final ArrayList<Interest> finalinter = inter;
		final AutoCompleteTextView autoComplete = (AutoCompleteTextView) mActivity.findViewById(R.id.autocompletion);


		ArrayAdapter adapter = new ArrayAdapter(mActivity,
				android.R.layout.simple_dropdown_item_1line, names);

		autoComplete.setAdapter(adapter);

		
		
	}

}

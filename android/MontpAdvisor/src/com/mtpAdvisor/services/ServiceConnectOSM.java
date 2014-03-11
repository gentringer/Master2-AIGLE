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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.activites.ListCateogry;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.parsers.JSONParser;
import com.mtpAdvisor.parsers.XMLParser;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

public class ServiceConnectOSM extends IntentService{

	InterestDbHelper db = new InterestDbHelper(this);

	static final String KEY_OSM = "osm"; // parent node
	static final String KEY_NODE = "node";
	static final String KEY_TAG = "tag";
	//public static String category = Activity_Fragments.inputString;

	private static final String TAG_MONUMENTS = "monuments";
	private static final String TAG_NUMBER = "number";
	private static final String TAG_MONUMENT_NAME = "monumentName";
	private static final String TAG_LONGITUDE = "longitude";
	private static final String TAG_LATITUDE = "latitude";

	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


	public ServiceConnectOSM() {
		super("ServiceGetBar");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(policy);
		System.out.println("start service get bar from OSM");

		XMLParser parser = new XMLParser();
		/*
		if(category.equals("Bars/Pubs")){
			URL = "http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=pub%7Cbar%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D";
			category="bars";
		}
		if(category.equals("Hôtels")){
			category="hotels";
			URL = "http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Btourism=hotel%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D";
		}
		if(category.equals("restaurant")){
			URL = "http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=restaurant%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D";
		}
		if(category.equals("parking")){
			URL = "http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=parking%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D";
		}
		if(category.equals("monument")){
			URL = "http://gillesentringer.com/php/monumentsMontpel.json";
		}*/

		ArrayList<String> listURL = new ArrayList<String>();
		ArrayList<String> listCategory = new ArrayList<String>();

		listURL.add("http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=pub%7Cbar%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D");
		listCategory.add("bars");
		listURL.add("http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Btourism=hotel%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D");		
		listCategory.add("hotels");
		listURL.add("http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=restaurant%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D");
		listCategory.add("restaurant");
		listURL.add("http://jxapi.openstreetmap.org/xapi/api/0.6/node%5Bamenity=parking%5D%5Bbbox=3.798913107612978,43.58082145070669,3.9362422091699947,43.63675382587661%5D");
		listCategory.add("parking");
		listURL.add("http://gillesentringer.com/php/monumentsMontpel.json");
		listCategory.add("monument");

		Interest b = null;
		ArrayList<Interest> al = new ArrayList<Interest>();
		ArrayList<Interest> al2 = new ArrayList<Interest>();

		for(int ii=0;ii<listURL.size();ii++){
			String URL = listURL.get(ii);

			if(!URL.equals("http://gillesentringer.com/php/monumentsMontpel.json")){
				String xml = parser.getXmlFromUrl(URL);



				try 
				{
					Document document = new SAXBuilder().build(new StringReader(xml));

					Element rootNode = document.getRootElement();

					//Log.d("tagg", rootNode.getName());
					List<Element> list = rootNode.getChildren(KEY_NODE);

					for (int i = 0; i < list.size(); i++)
					{

						b = new Interest();
						Element node = (Element) list.get(i);
						//Log.d("bla1",node.getAttributeValue("id"));
						Interest inter = null;
						b.setCategory(listCategory.get(ii));
						b.setLat(node.getAttributeValue("lat"));
						b.setLon(node.getAttributeValue("lon"));
						List<Element> list2 = node.getChildren(KEY_TAG);
						for (int j = 0; j < list2.size(); j++)
						{
							Element node2 = (Element) list2.get(j);

							if(node2.getAttributeValue("k").equalsIgnoreCase("name"))
							{
								//	inter = new Interest("bars", node.getAttributeValue("lat"), node.getAttributeValue("lon"), node2.getAttributeValue("v").toString());
								//Log.d("bla2", node2.getAttributeValue("v"));
								b.setNameInterest(node2.getAttributeValue("v").toString());
								b.setStatus(0);
								b.setNote(0);

							}

						}

						al.add(b);

					}
					//db.deleteAllBars();
					//Log.d("deleteAllBars", "Deleting...");
					int i=0;


					for(Interest ba : al)
					{

						if(ba.getNameInterest()==null)
						{
							//Log.d("null["+i+"]",ba.getLat()+" - "+ba.getLon()+" - "+ba.getName());
						}
						else
						{
							//b.addBar(ba);
							//Log.d("insertDB["+i+"]",ba.getLatitude()+" - "+ba.getLongitude()+" - "+ba.getName());	
							JSONParser jsonParser = new JSONParser();
							List<NameValuePair> paramss = new ArrayList<NameValuePair>();
							paramss.add(new BasicNameValuePair("category", listCategory.get(ii)));
							paramss.add(new BasicNameValuePair("nameinterest", ba.getNameInterest()));
							paramss.add(new BasicNameValuePair("lat", ba.getLat()));
							paramss.add(new BasicNameValuePair("lon", ba.getLon()));

							jsonParser.makeHttpRequest("http://gillesentringer.com/php/create_interest.php"
									,"POST", paramss);

						}
						i++;
					}

				}
				catch (JDOMException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else{
				JSONArray monuments = null;
				JSONParser jParser = new JSONParser();

				// getting JSON string from URL
				JSONObject json = jParser.getJSONFromUrl(URL);

				try {
					monuments = json.getJSONArray(TAG_MONUMENTS);				

					for(int i=0; i<monuments.length(); i++)
					{
						JSONObject c = monuments.getJSONObject(i);

						// Storing each json item in variable
						String number = c.getString(TAG_NUMBER);
						String name = c.getString(TAG_MONUMENT_NAME);
						String longitude = c.getString(TAG_LONGITUDE);
						String latitude = c.getString(TAG_LATITUDE);


						b = new Interest();

						b.setNameInterest(name);
						b.setLat(latitude);
						b.setLon(longitude);
						b.setStatus(0);
						b.setNote(0);
						al.add(b);
					}	





					for(Interest ba : al)
					{

						if(ba.getNameInterest()==null)
						{
							//Log.d("null["+i+"]",ba.getLat()+" - "+ba.getLon()+" - "+ba.getName());
						}
						else
						{
							//b.addBar(ba);
							//Log.d("insertDB["+i+"]",ba.getLatitude()+" - "+ba.getLongitude()+" - "+ba.getName());	
							JSONParser jsonParser = new JSONParser();
							List<NameValuePair> paramss = new ArrayList<NameValuePair>();
							paramss.add(new BasicNameValuePair("category", listCategory.get(ii)));
							paramss.add(new BasicNameValuePair("nameinterest", ba.getNameInterest()));
							paramss.add(new BasicNameValuePair("lat", ba.getLat()));
							paramss.add(new BasicNameValuePair("lon", ba.getLon()));

							jsonParser.makeHttpRequest("http://gillesentringer.com/php/create_interest.php"
									,"POST", paramss);

						}
					}
					this.stopSelf();


				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		publishResults();

	}

	private void publishResults() {
		Intent intent = new Intent("barsfromosm");

		sendBroadcast(intent);
	}


}
